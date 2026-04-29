package com.example.socapplication.model.dto.adminPermissionDto;

import java.time.OffsetDateTime;

public record ResponseAdminPermission(
        Long id,
        Long userId,
        Long permissionId,
        String permissionName,
        OffsetDateTime grantedAt,
        OffsetDateTime updatedAt,
        Long grantedBy
) {}