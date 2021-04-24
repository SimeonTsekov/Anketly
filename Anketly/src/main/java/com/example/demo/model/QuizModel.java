package com.example.demo.model;

import com.example.demo.entities.QuestionEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class QuizModel {
    private String name;
    private boolean isPublic;
    Set<QuestionModel> questionModels = new HashSet<>();

    public QuizModel(String name, boolean isPublic, Set<QuestionModel> questionModels) {
        this.name = name;
        this.isPublic = isPublic;
        this.questionModels = questionModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Set<QuestionModel> getQuestionModels() {
        return questionModels;
    }

    public void setQuestionModels(Set<QuestionModel> questionModels) {
        this.questionModels = questionModels;
    }
}
