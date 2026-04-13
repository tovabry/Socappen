package com.example.socapplication.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "faq_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appUserId;
    private Long faqId;
    private String ipAddress;
    private OffsetDateTime createdAt;
}
