package com.example.heartdiseasebackend.controller;

import com.example.heartdiseasebackend.model.Patient;
import com.example.heartdiseasebackend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // GET all patients
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // GET patients by nom and prenom
    @GetMapping("/patients/search")
    public ResponseEntity<Object> getPatientByNomPrenom(@RequestParam String nom, @RequestParam String prenom) {
        return patientRepository.findByNomAndPrenom(nom, prenom)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient non trouvé"));
    }


}
