package com.example.socapplication.model.dto.messageLogDto;

public record CreateMessageLog(
        Long appUserId,
        Long conversationId,
        String ipAddress
) {}
