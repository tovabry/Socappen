package com.example.socapplication.repository;


import com.example.socapplication.model.dto.QandADto.ResponseQAndA;
import com.example.socapplication.model.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends ListCrudRepository<Question, Long> {

    List<Question> findByStatus(String status);

    @Query("""
        SELECT new com.example.socapplication.model.dto.questionDto.ResponseQuestionWithAnswer(
            q.id,
            q.nickname,
            q.text,
            a.text
        )
        FROM Question q
        LEFT JOIN Answer a ON a.question.id = q.id
    """)
    List<ResponseQAndA> findAllQuestionsWithAnswers();

}
