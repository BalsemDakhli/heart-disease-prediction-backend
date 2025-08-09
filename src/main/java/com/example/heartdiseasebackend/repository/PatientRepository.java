package com.example.heartdiseasebackend.repository;

import com.example.heartdiseasebackend.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findByNomAndPrenom(String nom, String prenom);
}
