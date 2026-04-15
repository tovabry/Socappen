package com.example.socapplication.controller;

import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.service.AppUserService;
import com.example.socapplication.service.CurrentUser;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        String email = currentUser.getEmail();
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("email", email != null ? email : "Guest");
        response.put("role", currentUser.getRole());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public List<ResponseAppUser> getAllUsers() {
        return appUserService.findAllUsers();
    }
}