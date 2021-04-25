package com.example.demo.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long questionId;
    private boolean required;
    private boolean multipleChoice;
    private String description;
    @Column(length = 100000)
    private String base64image;
    @OneToMany
    @JoinColumn(name = "questionId")
    Set<AnswerEntity> answerEntities = new HashSet<>();

    public QuestionEntity() { }

    public QuestionEntity(boolean required, boolean multipleChoice, String description, String base64image) {
        this.required = required;
        this.multipleChoice = multipleChoice;
        this.description = description;
        this.base64image = base64image;
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

    public Set<AnswerEntity> getAnswerEntities() {
        return answerEntities;
    }

    public void setAnswerEntities(Set<AnswerEntity> answerEntities) {
        this.answerEntities = answerEntities;
    }
}
