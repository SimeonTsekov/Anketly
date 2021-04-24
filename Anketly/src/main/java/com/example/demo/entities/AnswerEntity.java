package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long answerId;
    public Long questionId;
    private String description;

    public AnswerEntity() { }

    public AnswerEntity(Long questionId, String description) {
        this.questionId = questionId;
        this.description = description;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
