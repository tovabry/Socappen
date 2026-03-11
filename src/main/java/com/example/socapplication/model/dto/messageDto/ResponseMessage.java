package com.example.socapplication.model.dto.messageDto;

import java.time.OffsetDateTime;

public record ResponseMessage (
        Long id,
        Long senderId,
        String content,
        OffsetDateTime sentAt
) {}
