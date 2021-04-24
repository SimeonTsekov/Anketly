package com.example.demo.service;

import com.example.demo.entities.AnswerEntity;
import com.example.demo.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public AnswerEntity insertAnswer(AnswerEntity answerEntity) {
        return answerRepository.save(answerEntity);
    }
}
