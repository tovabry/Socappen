package com.example.socapplication.controller;

import com.example.socapplication.model.dto.auditLogDto.ResponseAuditLog;
import com.example.socapplication.model.dto.authLogDto.ResponseAuthLog;
import com.example.socapplication.model.dto.messageLogDto.ResponseMessageLog;
import com.example.socapplication.service.AuditLogService;
import com.example.socapplication.service.AuthLogService;
import com.example.socapplication.service.MessageLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/logs")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'SYSADMIN')")
public class LogController {

    private final AuthLogService authLogService;
    private final MessageLogService messageLogService;
    private final AuditLogService auditLogService;

    @GetMapping("/auth")
    public ResponseEntity<List<ResponseAuthLog>> getAuthLogs() {
        return ResponseEntity.ok(authLogService.findAll());
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ResponseMessageLog>> getMessageLogs() {
        return ResponseEntity.ok(messageLogService.findAll());
    }

    @GetMapping("/audit")
    public ResponseEntity<List<ResponseAuditLog>> getAuditLogs() {
        return ResponseEntity.ok(auditLogService.findAll());
    }
}
