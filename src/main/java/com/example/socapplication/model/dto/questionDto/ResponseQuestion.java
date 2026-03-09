package com.example.socapplication.model.dto.questionDto;

import java.time.OffsetDateTime;

public record ResponseQuestion (
        Long id,
        String nickname,
        String text,
        OffsetDateTime sentAt,
        String status
) {

}

