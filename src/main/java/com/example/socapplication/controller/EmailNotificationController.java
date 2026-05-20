package com.example.socapplication.controller;

import com.example.socapplication.model.dto.emailNotificationDto.CreateEmailNotification;
import com.example.socapplication.model.dto.emailNotificationDto.ResponseEmailNotification;
import com.example.socapplication.model.dto.emailNotificationDto.UpdateEmailNotification;
import com.example.socapplication.service.EmailNotificationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-email")
public class EmailNotificationController {

    private final EmailNotificationService emailNotificationService;

    public EmailNotificationController(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @PostMapping
    public ResponseEmailNotification addEmail(@RequestBody CreateEmailNotification dto) {
        return emailNotificationService.addEmail(dto);
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @DeleteMapping("/{id}")
    public void removeEmail(@PathVariable Long id) {
       emailNotificationService.removeEmail(id);
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @PutMapping("/{id}")
    public ResponseEmailNotification updateEmail(@PathVariable Long id, @RequestBody UpdateEmailNotification dto) {
        return emailNotificationService.updateEmail(id, dto);
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @GetMapping
    public List<ResponseEmailNotification> findAll() {
        return emailNotificationService.findAll();
    }
}
