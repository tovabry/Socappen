package com.example.socapplication.model.dto.authLogDto;

import java.time.OffsetDateTime;

public record CreateAuthLog(
        Long userId,
        String ipAddress,
        boolean success,
        String failReason,
        OffsetDateTime loggedInAt
) {}
