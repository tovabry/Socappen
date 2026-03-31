package com.example.socapplication.model.dto.authLogDto;

import java.time.OffsetDateTime;

public record ResponseAuthLog(
        Long id,
        String email,
        String ipAddress,
        boolean success,
        String failReason,
        OffsetDateTime loggedInAt,
        OffsetDateTime loggedOutAt,
        OffsetDateTime createdAt
) {}
