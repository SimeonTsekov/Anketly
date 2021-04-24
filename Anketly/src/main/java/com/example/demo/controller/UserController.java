package com.example.demo.controller;

import com.example.demo.entities.QuizEntity;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/getUserQuizzes", method = RequestMethod.GET)
    public Set<QuizEntity> getUserQuizzes() throws Exception {
        return userService.getUserQuizzes();
    }
}
