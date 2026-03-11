package com.example.socapplication.model.dto.conversationDto;

import java.util.List;

public record CreateConversation(
        List<Long> participantIds
) {}
