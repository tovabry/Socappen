package com.example.socapplication.service;

import com.example.socapplication.model.dto.appUserDto.RegisterRequest;
import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.enums.user.AppUserRole;
import com.example.socapplication.enums.user.AppUserStatus;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseAppUser register(RegisterRequest request) {
        if (appUserRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        AppUser user = new AppUser();
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(AppUserRole.user);
        user.setStatus(AppUserStatus.active);
        user.setCreatedAt(OffsetDateTime.now());
        user.setLastActivityAt(OffsetDateTime.now());
        user.setIsOnline(false);

        AppUser saved = appUserRepository.save(user);

        return new ResponseAppUser(
                saved.getId(),
                saved.getEmail(),
                saved.getStatus(),
                saved.getRole(),
                saved.getIsOnline()
        );
    }

    public List<ResponseAppUser> findAllUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(appUser -> new ResponseAppUser(
                        appUser.getId(),
                        appUser.getEmail(),
                        appUser.getStatus(),
                        appUser.getRole(),
                        appUser.getIsOnline()
                ))
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}