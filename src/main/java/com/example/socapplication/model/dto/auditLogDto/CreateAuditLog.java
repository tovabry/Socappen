package com.example.socapplication.model.dto.auditLogDto;

public record CreateAuditLog(
        Long appUserId,
        String ipAddress,
        String reason
) {}