package com.example.socapplication.service;


import com.example.socapplication.model.dto.questionDto.ResponseQuestion;
import com.example.socapplication.model.entity.Question;
import com.example.socapplication.model.mapper.QuestionMapper;
import com.example.socapplication.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public List<ResponseQuestion> findAll() {
        return questionRepository.findAll()
                .stream()
                .map(question -> new ResponseQuestion(
                        question.getId(),
                        question.getNickname(),
                        question.getText(),
                        question.getSentAt(),
                        question.getStatus()

                ))
                .toList();
    }

    public List<ResponseQuestion> findPending() {
        List<Question> questions = questionRepository.findByStatus("pending");

        System.out.println("FOUND QUESTIONS: " + questions.size());

        return questions.stream()
                .map(question -> new ResponseQuestion(
                        question.getId(),
                        question.getNickname(),
                        question.getText(),
                        question.getSentAt(),
                        question.getStatus()
                ))
                .toList();
    }

    public ResponseQuestion findQuestionById(Long id) {
        return questionRepository.findById(id)
                .map(QuestionMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

