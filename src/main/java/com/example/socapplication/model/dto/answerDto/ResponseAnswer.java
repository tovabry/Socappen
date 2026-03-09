package com.example.socapplication.model.dto.answerDto;

import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Question;

import java.time.OffsetDateTime;

public record ResponseAnswer (
    Long id,
    Question question,
    AppUser appUser,
    String text,
    OffsetDateTime sentAt
) {
}
