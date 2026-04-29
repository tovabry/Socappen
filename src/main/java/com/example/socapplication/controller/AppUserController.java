package com.example.socapplication.controller;

import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.service.AppUserService;
import com.example.socapplication.service.CurrentUser;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class AppUserController {
    private final CurrentUser currentUser;

    AppUserService appUserService;

    public AppUserController(CurrentUser currentUser, AppUserService appUserService) {
        this.currentUser = currentUser;
        this.appUserService = appUserService;

    }
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Long id = currentUser.getUserId();
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("email", appUserService.getDecryptedEmailById(id));
        response.put("role", currentUser.getRole());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("(hasRole('ADMIN') or hasRole ('SYSADMIN')) and @permissionService.hasPermission(authentication, 'manage_user')")
    @GetMapping("/all")
    public List<ResponseAppUser> getAllUsers() {
        return appUserService.findAllUsers();
    }

    @PreAuthorize("(hasRole('ADMIN') or hasRole ('SYSADMIN')) and @permissionService.hasPermission(authentication, 'manage_user')")
    @GetMapping("/role")
    public List<AppUser> getUsersByRole(@RequestParam String role) {
        return appUserService.findAllUsersByRole(role);
    }

    @PreAuthorize("(hasRole('ADMIN') or hasRole ('SYSADMIN')) and @permissionService.hasPermission(authentication, 'manage_user')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}