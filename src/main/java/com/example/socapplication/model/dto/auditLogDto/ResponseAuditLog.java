package com.example.socapplication.model.dto.auditLogDto;

import java.time.OffsetDateTime;

public record ResponseAuditLog(
        Long id,
        Long appUserId,
        String ipAddress,
        String reason,
        OffsetDateTime createdAt
) {}
