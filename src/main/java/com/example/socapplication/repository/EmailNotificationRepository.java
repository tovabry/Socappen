package com.example.socapplication.repository;

import com.example.socapplication.model.entity.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {}
