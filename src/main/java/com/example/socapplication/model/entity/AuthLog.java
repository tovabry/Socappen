package com.example.socapplication.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "auth_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appUserId;
    private String ipAddress;
    private boolean success;
    private String failReason;
    private OffsetDateTime loggedInAt;
    private OffsetDateTime loggedOutAt;
    private OffsetDateTime createdAt;
}