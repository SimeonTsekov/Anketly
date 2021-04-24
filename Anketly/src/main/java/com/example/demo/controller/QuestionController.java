package com.example.demo.controller;

import com.example.demo.entities.QuestionEntity;
import com.example.demo.service.QuestionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/createQuestion", method = RequestMethod.POST)
    public QuestionEntity createQuestion(@RequestBody QuestionEntity questionEntity) throws Exception {
        return questionService.insertQuestion(questionEntity);
    }

}
