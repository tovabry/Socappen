package com.example.socapplication.model.dto.questionDto;

import org.jspecify.annotations.NonNull;

public record CreateQuestion (
        @NonNull String nickname,
        @NonNull String text
) {
}
