package com.example.socapplication.controller;

import com.example.socapplication.service.CurrentUser;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class AppUserController {
    private final CurrentUser currentUser;

    public AppUserController(CurrentUser currentUser) {
        this.currentUser = currentUser;

    }
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser() {
        String email = currentUser.getEmail();
        Map<String, String> response = new HashMap<>();
        response.put("email", email != null ? email : "Guest");
        return ResponseEntity.ok(response);
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