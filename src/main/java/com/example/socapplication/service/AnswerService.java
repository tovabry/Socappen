package com.example.socapplication.service;


import com.example.socapplication.model.dto.answerDto.ResponseAnswer;
import com.example.socapplication.model.entity.Answer;
import com.example.socapplication.model.entity.Question;
import com.example.socapplication.model.mapper.AnswerMapper;
import com.example.socapplication.repository.AnswerRepository;
import com.example.socapplication.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public List<ResponseAnswer> findAll() {
        return answerRepository.findAll()
                .stream()
                .map(answer -> new ResponseAnswer(
                        answer.getId(),
                        answer.getQuestion(),
                        answer.getAppUser(),
                        answer.getText(),
                        answer.getSentAt()
                ))
                .toList();
    }

    public ResponseAnswer findById(Long id) {
        return answerRepository.findById(id)
                .map(AnswerMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void createAnswer(Long questionID, String text, Long userId) {
        Question question = questionRepository.findById(questionID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setText(text);

        answerRepository.save(answer);

        question.setStatus("approved");
        questionRepository.save(question);

    }

}
