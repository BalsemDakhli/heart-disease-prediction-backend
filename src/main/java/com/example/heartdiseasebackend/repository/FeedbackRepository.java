package com.example.heartdiseasebackend.repository;

import com.example.heartdiseasebackend.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
}
