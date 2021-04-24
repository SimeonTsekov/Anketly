package com.example.demo.service;

import com.example.demo.entities.QuizEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.QuizRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuizEntity insertQuiz(QuizEntity quizEntity) {
        return quizRepository.save(quizEntity);
    }
}
