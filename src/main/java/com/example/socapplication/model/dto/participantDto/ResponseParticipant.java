package com.example.socapplication.model.dto.participantDto;

import java.time.OffsetDateTime;

public record ResponseParticipant (
        Long id,
        Long userId,
        String email,
        OffsetDateTime joinedAt
) {}