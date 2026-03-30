package com.example.socapplication.model.dto.appUserDto;

import com.example.socapplication.enums.user.AppUserRole;
import com.example.socapplication.enums.user.AppUserStatus;

public record ResponseAppUser(
Long id,
String email,
AppUserStatus status,
AppUserRole role,
boolean is_online
) {
}

