package com.example.demo.repository;

import com.example.demo.entities.UserAnswerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswerEntity, Long> {
}
