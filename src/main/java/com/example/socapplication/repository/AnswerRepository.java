package com.example.socapplication.repository;

import com.example.socapplication.model.entity.Answer;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends ListCrudRepository<Answer, Long> {

}
