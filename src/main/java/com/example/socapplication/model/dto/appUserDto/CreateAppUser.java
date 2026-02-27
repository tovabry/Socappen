package com.example.socapplication.model.dto.appUserDto;

import org.jspecify.annotations.NonNull;

public record CreateAppUser (@NonNull String email,
                             @NonNull String password,
                             @NonNull String role,
                             @NonNull String status
                             ){

}