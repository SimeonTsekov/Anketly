package com.example.demo.entities;

import com.example.demo.model.UserModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String passwordHash;
    @OneToMany()
    @JoinColumn(name = "userId", referencedColumnName = "id")
    Set<QuizEntity> quizEntities = new HashSet<>();

    public UserEntity() { }

    public UserEntity(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<QuizEntity> getQuizEntities() {
        return quizEntities;
    }

    public void setQuizEntities(Set<QuizEntity> quizEntities) {
        this.quizEntities = quizEntities;
    }

    public UserModel toUserDetails()
    {
        return new UserModel(this.username, this.passwordHash);
    }
}
