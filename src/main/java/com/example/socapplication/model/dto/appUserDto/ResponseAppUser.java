package com.example.socapplication.model.dto.appUserDto;

public record ResponseAppUser(
Integer id,
String email,
String status,
String role,
boolean is_online
) {
}

