package com.example.demo.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "quizzes")
public class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quizId;
    private String name;
    private boolean isPublic;
    private boolean isOpen;
    private String uuid;
    public Long userId;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "questionQuiz", joinColumns = @JoinColumn(name = "quizId"),
            inverseJoinColumns = @JoinColumn(name = "questionId"))
    Set<QuestionEntity> questionEntities = new HashSet<>();

    public QuizEntity() { }

    public QuizEntity(String name, boolean isPublic, boolean isOpen, String uuid, Long userId, Set<QuestionEntity> questionEntities) {
        this.name = name;
        this.isPublic = isPublic;
        this.isOpen = isOpen;
        this.uuid = uuid;
        this.userId = userId;
        this.questionEntities = questionEntities;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<QuestionEntity> getQuestionEntities() {
        return questionEntities;
    }

    public void setQuestionEntities(Set<QuestionEntity> questionEntities) {
        this.questionEntities = questionEntities;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
