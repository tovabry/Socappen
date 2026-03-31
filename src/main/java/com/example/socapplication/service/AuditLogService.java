package com.example.socapplication.service;

import com.example.socapplication.model.dto.auditLogDto.CreateAuditLog;
import com.example.socapplication.model.dto.auditLogDto.ResponseAuditLog;
import com.example.socapplication.model.entity.AuditLog;
import com.example.socapplication.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogService {
    private final AuditLogRepository repository;

    public void log(CreateAuditLog dto) {
        repository.save(AuditLog.builder()
                .appUserId(dto.appUserId())
                .ipAddress(dto.ipAddress())
                .reason(dto.reason())
                .createdAt(OffsetDateTime.now())
                .build());
    }

    public List<ResponseAuditLog> findAll() {
        return repository.findAll().stream()
                .map(l -> new ResponseAuditLog(
                        l.getId(),
                        l.getAppUserId(),
                        l.getIpAddress(),
                        l.getReason(),
                        l.getCreatedAt()))
                .toList();
    }
}
