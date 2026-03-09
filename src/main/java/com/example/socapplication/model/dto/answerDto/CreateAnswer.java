package com.example.socapplication.model.dto.answerDto;

import org.jspecify.annotations.NonNull;

public record CreateAnswer (
        @NonNull Long questionId,
        @NonNull Long AppUserId,
        @NonNull String text
) {
}
