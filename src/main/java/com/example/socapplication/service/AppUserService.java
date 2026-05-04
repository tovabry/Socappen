package com.example.socapplication.service;

import com.example.socapplication.model.dto.appUserDto.RegisterRequest;
import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Role;
import com.example.socapplication.repository.AdminPermissionRepository;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.enums.user.AppUserStatus;
import com.example.socapplication.repository.RoleRepository;
import com.example.socapplication.util.HashUtil;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EncryptionService encryptionService;
    private final AdminPermissionRepository adminPermissionRepository;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, EncryptionService encryptionService, AdminPermissionRepository adminPermissionRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.encryptionService = encryptionService;
        this.adminPermissionRepository = adminPermissionRepository;
    }

    public ResponseAppUser register(RegisterRequest request) {
        if (appUserRepository.findByEmailHash(HashUtil.hashEmail(request.email())).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        //New accounts gets "user" role by default
        Role userRole = roleRepository.findByName("USER")
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
        AppUser user = appUserRepository.findByEmailHash(HashUtil.hashEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toUpperCase()));

        adminPermissionRepository.findByAppUser_Id(user.getId())
                .forEach(ap -> authorities.add(new SimpleGrantedAuthority(ap.getPermission().getName())));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public List<AppUser> findAllUsersByRole(String role) {
        return appUserRepository.findByRole_Name(role);
    }

    public void updateAppUserIsOnline(Long id, boolean isOnline) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setIsOnline(isOnline);
        appUserRepository.save(user);
    }

    public void updateAppUserStatus(Long id, AppUserStatus status) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setStatus(status);
        appUserRepository.save(user);
    }

    public void updateStatus(Long id, AppUserStatus status) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setStatus(status);
    }

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmailHash(HashUtil.hashEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public String getDecryptedEmailById(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return encryptionService.decrypt(user.getEmail());
    }

    public void deleteUser(Long id) {
        if (!appUserRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        appUserRepository.deleteById(id);
    }

}