package com.example.socapplication.model.dto.authLogDto;

import java.time.OffsetDateTime;

public record CreateAuthLog(
        String email,
        String ipAddress,
        boolean success,
        String failReason,
        OffsetDateTime loggedInAt
) {}
