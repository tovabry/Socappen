package com.example.socapplication.model.dto.appUserDto;

import com.example.socapplication.enums.user.AppUserStatus;

public record ResponseAppUser(
Long id,
String email,
AppUserStatus status,
String role,
boolean is_online
) {
}

