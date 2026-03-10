package com.example.socapplication.controller;

import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.service.AppUserService;
import com.example.socapplication.service.CurrentUser;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class AppUserController {
    private final CurrentUser currentUser;

    AppUserService appUserService;

    public AppUserController(CurrentUser currentUser, AppUserService appUserService) {
        this.currentUser = currentUser;
        this.appUserService = appUserService;

    }
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser() {
        String email = currentUser.getEmail();
        Map<String, String> response = new HashMap<>();
        response.put("email", email != null ? email : "Guest");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public List<ResponseAppUser> getAllUsers() {
        return appUserService.findAllUsers();
    }


  /*  @GetMapping("/home")
  public String home(Model model) {
        String email = currentUser.getEmail();
        if (email == null) {
            email = "Guest"; // or leave it blank
        }
        model.addAttribute("email", email);
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        var authentication = currentUser.getRole();
        var user = currentUser.getEmail();
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", user);
        return "profile";
    }
*/
}