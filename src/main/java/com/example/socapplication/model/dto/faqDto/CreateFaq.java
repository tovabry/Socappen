package com.example.socapplication.model.dto.faqDto;

import org.jspecify.annotations.NonNull;

public record CreateFaq (@NonNull String question,
                         @NonNull String Answer) {
}
