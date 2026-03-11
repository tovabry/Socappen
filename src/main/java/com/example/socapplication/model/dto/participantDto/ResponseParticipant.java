package com.example.socapplication.model.dto.participantDto;

import java.time.OffsetDateTime;

public record ResponseParticipant (
        Long id,
        Long userId,
        OffsetDateTime joinedAt
) {}