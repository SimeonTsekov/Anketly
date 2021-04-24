package com.example.demo.service;

import com.example.demo.entities.QuizEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity insertUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public Set<QuizEntity> getUserQuizzes() {
        Optional<UserEntity> user = findByUsername(((UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        return user.get().getQuizEntities();
    }
}
