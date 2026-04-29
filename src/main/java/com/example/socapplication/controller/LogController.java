package com.example.socapplication.controller;

import com.example.socapplication.model.dto.auditLogDto.ResponseAuditLog;
import com.example.socapplication.model.dto.authLogDto.ResponseAuthLog;
import com.example.socapplication.model.dto.faqLogDto.ResponseFaqLog;
import com.example.socapplication.model.dto.messageLogDto.ResponseMessageLog;
import com.example.socapplication.model.dto.postLogDto.ResponsePostLog;
import com.example.socapplication.service.*;
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
    private final PostLogService postLogService;
    private final FaqLogServie faqLogService;

    @PreAuthorize("(hasRole('ADMIN') or hasRole ('SYSADMIN')) and @permissionService.hasPermission(authentication, 'view_logs')")
    @GetMapping("/auth")
    public ResponseEntity<List<ResponseAuthLog>> getAuthLogs() {
        return ResponseEntity.ok(authLogService.findAll());
    }

    @PreAuthorize("(hasRole('ADMIN') or hasRole ('SYSADMIN')) and @permissionService.hasPermission(authentication, 'view_logs')")
    @GetMapping("/messages")
    public ResponseEntity<List<ResponseMessageLog>> getMessageLogs() {
        return ResponseEntity.ok(messageLogService.findAll());
    }

    @PreAuthorize("(hasRole('ADMIN') or hasRole ('SYSADMIN')) and @permissionService.hasPermission(authentication, 'view_logs')")
    @GetMapping("/audit")
    public ResponseEntity<List<ResponseAuditLog>> getAuditLogs() {
        return ResponseEntity.ok(auditLogService.findAll());
    }

    @PreAuthorize("(hasRole('ADMIN') or hasRole ('SYSADMIN')) and @permissionService.hasPermission(authentication, 'view_logs')")
    @GetMapping("/post")
    public ResponseEntity<List<ResponsePostLog>> getPostLogs(){
        return ResponseEntity.ok(postLogService.findAll());
    }

    @PreAuthorize("(hasRole('ADMIN') or hasRole ('SYSADMIN')) and @permissionService.hasPermission(authentication, 'view_logs')")
    @GetMapping("/faq")
    public ResponseEntity<List<ResponseFaqLog>> getFaqLogs() {
        return ResponseEntity.ok(faqLogService.findAll());
    }
}
