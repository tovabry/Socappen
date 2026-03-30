package com.example.socapplication.controller;

import com.example.socapplication.model.dto.adminPermissionDto.AddAdminPermission;
import com.example.socapplication.model.dto.adminPermissionDto.ResponseAdminPermission;
import com.example.socapplication.model.dto.permissionDto.ResponsePermission;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.service.AdminPermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sysadmin")
public class AdminPermissionController {

    private final AdminPermissionService adminPermissionService;

    public AdminPermissionController(AdminPermissionService adminPermissionService) {
        this.adminPermissionService = adminPermissionService;
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<ResponsePermission>> findAllPermissions() {
        return ResponseEntity.ok(adminPermissionService.findAllPermissions());
    }

    @GetMapping("/permissions/{userId}")
    public ResponseEntity<List<ResponseAdminPermission>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(adminPermissionService.findByUserId(userId));
    }

    @PostMapping("/permissions")
    public ResponseEntity<Void> grantPermission(@RequestBody AddAdminPermission dto,
                                                @AuthenticationPrincipal AppUser currentUser) {
        adminPermissionService.grantPermission(dto, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/permissions/{userId}/{permissionId}")
    public ResponseEntity<Void> revokePermission(@PathVariable Long userId,
                                                 @PathVariable Integer permissionId) {
        adminPermissionService.revokePermission(userId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/promote/{userId}")
    public ResponseEntity<Void> promoteToAdmin(@PathVariable Long userId) {
        adminPermissionService.promoteToAdmin(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/demote/{userId}")
    public ResponseEntity<Void> demoteToUser(@PathVariable Long userId) {
        adminPermissionService.demoteToUser(userId);
        return ResponseEntity.noContent().build();
    }
}