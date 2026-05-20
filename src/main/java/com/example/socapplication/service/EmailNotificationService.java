package com.example.socapplication.service;

import com.example.socapplication.model.dto.emailNotificationDto.CreateEmailNotification;
import com.example.socapplication.model.dto.emailNotificationDto.ResponseEmailNotification;
import com.example.socapplication.model.dto.emailNotificationDto.UpdateEmailNotification;
import com.example.socapplication.model.entity.EmailNotification;
import com.example.socapplication.repository.EmailNotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmailNotificationService {

    private final EmailNotificationRepository emailNotificationRepository;
    private final EmailService emailService;

    public EmailNotificationService(EmailNotificationRepository emailNotificationRepository, EmailService emailService) {
        this.emailNotificationRepository = emailNotificationRepository;
        this.emailService = emailService;
    }


    public ResponseEmailNotification addEmail (CreateEmailNotification dto) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setEmail(dto.email());
        EmailNotification saved = emailNotificationRepository.save(emailNotification);
        return new ResponseEmailNotification(saved.getId(), saved.getEmail());

    }

    public ResponseEmailNotification updateEmail(Long id, UpdateEmailNotification dto) {
        EmailNotification emailNotification = emailNotificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));
        emailNotification.setEmail(dto.email());
        EmailNotification saved = emailNotificationRepository.save(emailNotification);
        return new ResponseEmailNotification(saved.getId(), saved.getEmail());
    }

    public void removeEmail(Long id) {
        emailNotificationRepository.deleteById(id);
    }

    public List<ResponseEmailNotification> findAll() {
        return emailNotificationRepository.findAll()
                .stream()
                .map(n -> new ResponseEmailNotification(n.getId(), n.getEmail()))
                .toList();
    }

    public void notifyAll(String subject, String body) {
        emailNotificationRepository.findAll()
                .forEach(n -> emailService.sendNotification(n.getEmail(), subject, body));
    }
}