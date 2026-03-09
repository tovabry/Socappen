package com.example.socapplication.controller;

import com.example.socapplication.model.dto.questionDto.ResponseQuestion;
import com.example.socapplication.model.entity.Question;
import com.example.socapplication.service.QuestionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/pending")
    public List<ResponseQuestion> getPendingQuestions() {
        return questionService.findPending();
    }

    @GetMapping("/{id}")
    public ResponseQuestion questionById(@PathVariable Long id) {
        return questionService.findQuestionById(id);
    }

}
