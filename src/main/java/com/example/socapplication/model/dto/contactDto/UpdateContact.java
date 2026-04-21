package com.example.socapplication.model.dto.contactDto;

public record UpdateContact(
        Long userId,
        String title,
        String img_url,
        String mail,
        String phone
) {
}
