package com.example.socapplication.model.dto.answerDto;

public record UpdateAnswer (
        Long questionId,
        Long AppUserId,
        String text
) {
}
