package com.example.demo.controller;

import com.example.demo.entities.AnswerEntity;
import com.example.demo.entities.QuestionEntity;
import com.example.demo.entities.QuizEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.model.QuestionModel;
import com.example.demo.model.QuizModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.AnswerService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.QuizService;
import com.example.demo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class QuizController {

    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;
    private final QuizService quizService;

    public QuizController(QuestionService questionService, UserService userService, AnswerService answerService, QuizService quizService) {
        this.questionService = questionService;
        this.userService = userService;
        this.answerService = answerService;
        this.quizService = quizService;
    }

    @RequestMapping(value = "/createQuiz", method = RequestMethod.POST)
    public QuizEntity createQuiz(@RequestBody QuizModel quizModel) throws Exception {
        Set<QuestionModel> questionModels = quizModel.getQuestionModels();

        Set<QuestionEntity> questionEntities = questionModels.stream().
                map(q ->
                    new QuestionEntity(q.isRequired()
                    ,q.isMultipleChoice()
                    ,q.getDescription()
                    ,q.getBase64image())).
                collect(Collectors.toSet());

        UUID uuid = UUID.randomUUID();
        Optional<UserEntity> user = userService.findByUsername(((UserModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        QuizEntity quizEntity = new QuizEntity(quizModel.getName(),
                quizModel.isPublic(),
                true,
                uuid.toString(),
                user.get().getId(),
                questionEntities);

        QuizEntity quiz = quizService.insertQuiz(quizEntity);

        quiz.getQuestionEntities().forEach(q -> {
            q.getAnswerEntities().forEach(qu -> {
                answerService.insertAnswer(new AnswerEntity(q.questionId, qu.getDescription()));
            });
        });

        return quiz;
    }

    @RequestMapping(value = "/getPublicQuizzes", method = RequestMethod.GET)
    public Set<QuizEntity> getPublicQuizzes() throws Exception {
        return quizService.getPublicQuizzes();
    }

    @RequestMapping(value = "/closeQuiz", method = RequestMethod.PATCH)
    public QuizEntity closeQuiz(@RequestBody QuizEntity quizEntity) {
        quizEntity.setOpen(false);
        return quizService.insertQuiz(quizEntity);
    }

}
