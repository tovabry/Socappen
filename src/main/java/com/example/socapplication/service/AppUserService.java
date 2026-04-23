package com.example.socapplication.service;

import com.example.socapplication.model.dto.appUserDto.RegisterRequest;
import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Role;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.enums.user.AppUserStatus;
import com.example.socapplication.repository.RoleRepository;
import com.example.socapplication.util.HashUtil;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EncryptionService encryptionService;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, EncryptionService encryptionService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.encryptionService = encryptionService;
    }

    public ResponseAppUser register(RegisterRequest request) {
        if (appUserRepository.findByEmailHash(HashUtil.hashEmail(request.email())).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        //New accounts gets "user" role by default
        Role userRole = roleRepository.findByName("user")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found"));

        AppUser user = new AppUser();
        user.setEmail(encryptionService.encrypt(request.email()));
        user.setEmailHash(HashUtil.hashEmail(request.email()));
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(userRole);
        user.setStatus(AppUserStatus.active);
        user.setCreatedAt(OffsetDateTime.now());
        user.setLastActivityAt(OffsetDateTime.now());
        user.setIsOnline(false);

        AppUser saved = appUserRepository.save(user);

        return new ResponseAppUser(
                saved.getId(),
                encryptionService.decrypt(saved.getEmail()),
                saved.getStatus(),
                saved.getRole().getName(),
                saved.getIsOnline()
        );
    }

    public List<ResponseAppUser> findAllUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(appUser -> {
                    try {
                        return new ResponseAppUser(
                                appUser.getId(),
                                encryptionService.decrypt(appUser.getEmail()),
                                appUser.getStatus(),
                                appUser.getRole().getName(),
                                appUser.getIsOnline()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmailHash(HashUtil.hashEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmailHash(HashUtil.hashEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

}