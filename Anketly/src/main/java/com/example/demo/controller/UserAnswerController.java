package com.example.demo.controller;

import com.example.demo.entities.UserAnswerEntity;
import com.example.demo.model.UserAnswerModel;
import com.example.demo.service.UserAnswerService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class UserAnswerController {
    private final UserAnswerService userAnswerService;

    public UserAnswerController(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public Iterable vote(@RequestBody Set<UserAnswerModel> userAnswerModels) throws Exception {
        Set<UserAnswerEntity> userAnswerEntities = userAnswerModels.stream().
                map(ua ->
                        new UserAnswerEntity(ua.getQuestionId(), ua.getAnswerId())).
                collect(Collectors.toSet());

        return userAnswerService.insertUserAnswers(userAnswerEntities);
    }

}
