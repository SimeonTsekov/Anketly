package com.example.demo.model;

import java.util.Set;

public class QuestionModel {
    private boolean required;
    private boolean multipleChoice;
    private String description;
    private String base64image;
    private Set<AnswerModel> answerModels;

    public QuestionModel(boolean required, boolean multipleChoice, String description, String base64image, Set<AnswerModel> answerModels) {
        this.required = required;
        this.multipleChoice = multipleChoice;
        this.description = description;
        this.base64image = base64image;
        this.answerModels = answerModels;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase64image() {
        return base64image;
    }

    public void setBase64image(String base64image) {
        this.base64image = base64image;
    }

    public Set<AnswerModel> getAnswerModels() {
        return answerModels;
    }

    public void setAnswerModels(Set<AnswerModel> answerModels) {
        this.answerModels = answerModels;
    }
}
