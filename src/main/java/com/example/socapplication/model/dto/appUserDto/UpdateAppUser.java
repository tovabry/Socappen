package com.example.socapplication.model.dto.appUserDto;


public record UpdateAppUser(
        String email,
        String password,
        String status,
        String role
        ) {
}
