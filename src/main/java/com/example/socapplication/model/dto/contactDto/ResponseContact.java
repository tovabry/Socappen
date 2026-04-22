package com.example.socapplication.model.dto.contactDto;

import java.time.OffsetDateTime;

public record ResponseContact(
        Long id,
        Long userId,
        String title,
        String imgUrl,
        String mail,
        String phone,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
