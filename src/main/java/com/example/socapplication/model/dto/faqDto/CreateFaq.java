package com.example.socapplication.model.dto.faqDto;

import org.jspecify.annotations.NonNull;

public record CreateFaq (@NonNull Long userId,
                         @NonNull String question,
                         @NonNull String Answer) {
}
