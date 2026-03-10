package com.example.socapplication.model.dto.appUserDto;

import com.example.socapplication.user.AppUserRole;
import com.example.socapplication.user.AppUserStatus;

public record ResponseAppUser(
Long id,
String email,
AppUserStatus status,
AppUserRole role,
boolean is_online
) {
}

