package com.example.heartdiseasebackend.controller;

import com.example.heartdiseasebackend.service.HeartDiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HeartDiseaseController {

    @Autowired
    private HeartDiseaseService heartDiseaseService;

    @PostMapping("/api/heart-risk")
    public Map<String, Object> predictHeartDisease(@RequestBody Map<String, Object> patientData) {
        return heartDiseaseService.getPrediction(patientData);
    }
}
