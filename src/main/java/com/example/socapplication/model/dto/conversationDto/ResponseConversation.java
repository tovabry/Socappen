package com.example.socapplication.model.dto.conversationDto;

import com.example.socapplication.enums.user.ConversationStatus;

import java.time.OffsetDateTime;

public record ResponseConversation(
        Long id,
        ConversationStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime lastActivityAt
) {}