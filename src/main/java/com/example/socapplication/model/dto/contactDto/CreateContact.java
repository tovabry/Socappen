package com.example.socapplication.model.dto.contactDto;

public record CreateContact(
        Long userId,
        String title,
        String img_url,
        String mail,
        String phone
) {}