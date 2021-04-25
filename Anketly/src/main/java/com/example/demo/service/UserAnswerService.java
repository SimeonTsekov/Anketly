package com.example.demo.service;

import com.example.demo.entities.UserAnswerEntity;
import com.example.demo.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    public UserAnswerService(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    public Iterable insertUserAnswers(Set<UserAnswerEntity> userAnswerEntities) {
        return userAnswerRepository.saveAll(userAnswerEntities);
    }
}
