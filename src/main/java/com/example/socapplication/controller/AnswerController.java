package com.example.socapplication.controller;

import com.example.socapplication.service.AnswerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    AnswerService answerService;

    public  AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

}
