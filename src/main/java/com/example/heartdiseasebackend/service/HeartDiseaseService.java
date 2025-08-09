package com.example.heartdiseasebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HeartDiseaseService {

    @Autowired
    private RestTemplate restTemplate;

    private final String flaskApiUrl = "http://localhost:5000/predict";

    public Map<String, Object> getPrediction(Map<String, Object> patientData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(patientData, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, request, Map.class);

        return response.getBody();
    }
}
