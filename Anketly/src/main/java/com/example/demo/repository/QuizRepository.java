package com.example.demo.repository;

import com.example.demo.entities.QuizEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuizRepository extends CrudRepository<QuizEntity, Long> {

    public Set<QuizEntity> findByIsPublicIsTrueAndIsOpenIsTrue();
}
