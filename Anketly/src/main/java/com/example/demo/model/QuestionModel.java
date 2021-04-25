package com.example.demo.model;

import java.util.Set;

public class QuestionModel {
    private boolean required;
    private boolean multipleChoice;
    private String description;
    private String base64image;
    private Set<String> answers;

    public QuestionModel(boolean required, boolean multipleChoice, String description, String base64image, Set<String> answers) {
        this.required = required;
        this.multipleChoice = multipleChoice;
        this.description = description;
        this.base64image = base64image;
        this.answers = answers;
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

    public Set<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
    }
}
