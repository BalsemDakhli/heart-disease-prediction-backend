package com.example.heartdiseasebackend.controller;

import com.example.heartdiseasebackend.model.Patient;
import com.example.heartdiseasebackend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestBody Map<String, Object> input) {
        try {
            // 🔸 Nettoyage des données envoyées à Flask
            Map<String, Object> flaskInput = new HashMap<>(input);
            flaskInput.remove("nom");
            flaskInput.remove("prenom");

            // 🔸 Appel du microservice Flask
            RestTemplate restTemplate = new RestTemplate();
            String flaskUrl = "http://flask-service:5000/predict";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(flaskInput, headers);

            ResponseEntity<Map> flaskResponse = restTemplate.exchange(
                    flaskUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            Map<String, Object> response = flaskResponse.getBody();

            // 🔸 Création et sauvegarde du patient
            Patient patient = new Patient();
            patient.setAge(asInt(input.get("age")));
            patient.setSex(asInt(input.get("sex")));
            patient.setCp(asInt(input.get("cp")));
            patient.setTrestbps(asInt(input.get("trestbps")));
            patient.setChol(asInt(input.get("chol")));
            patient.setFbs(asInt(input.get("fbs")));
            patient.setRestecg(asInt(input.get("restecg")));
            patient.setThalach(asInt(input.get("thalach")));
            patient.setExang(asInt(input.get("exang")));
            patient.setOldpeak(asDouble(input.get("oldpeak")));
            patient.setSlope(asInt(input.get("slope")));
            patient.setCa(asInt(input.get("ca")));
            patient.setThal(asInt(input.get("thal")));
            patient.setNom((String) input.get("nom"));
            patient.setPrenom((String) input.get("prenom"));
            patient.setPrediction(asInt(response.get("heart_disease_risk")));
            patient.setProbability(asDouble(response.get("probability")));

            patientRepository.save(patient);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur lors de la prédiction : " + e.getMessage()));
        }
    }

    private int asInt(Object o) {
        if (o instanceof Integer) return (int) o;
        if (o instanceof Double) return ((Double) o).intValue();
        if (o instanceof String) return Integer.parseInt((String) o);
        throw new IllegalArgumentException("Impossible de convertir en int : " + o);
    }

    private double asDouble(Object o) {
        if (o instanceof Double) return (double) o;
        if (o instanceof Integer) return ((Integer) o).doubleValue();
        if (o instanceof String) return Double.parseDouble((String) o);
        throw new IllegalArgumentException("Impossible de convertir en double : " + o);
    }
}
