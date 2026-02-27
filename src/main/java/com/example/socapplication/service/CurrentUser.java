package com.example.socapplication.service;

import com.example.socapplication.repository.AppUserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public final class CurrentUser {
    private final AppUserRepository appUserRepository;


    public CurrentUser(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public Integer getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null; // anonymous user
        }

        try {
            return Integer.parseInt(auth.getName());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null; // anonymous user
        }

        try {
            Integer id = Integer.parseInt(auth.getName());
            return appUserRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getEmail();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null; // anonymous user
        }

        try {
            Integer id = Integer.parseInt(auth.getName());
            return appUserRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getRole().name();
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
