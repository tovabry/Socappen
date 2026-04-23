package com.example.socapplication.service;

import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public final class CurrentUser {
    private final AppUserRepository appUserRepository;


    public CurrentUser(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }

        AppUser user = (AppUser) auth.getPrincipal();

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found in security context");
        }

        return user.getId();
    }

    public String getEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return auth.getName(); // already the email from JWT sub
    }

    public String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null; // anonymous user
        }

        try {
            Long id = Long.parseLong(auth.getName());
            return appUserRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getRole().getName();
        } catch (NumberFormatException _) {
            return null;
        }
    }

}
