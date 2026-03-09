package com.example.socapplication.model.dto.QandADto;

public record ResponseQAndA(
        Long QuestionId,
        String nickname,
        String questionText,
        String answertext
) {
}

