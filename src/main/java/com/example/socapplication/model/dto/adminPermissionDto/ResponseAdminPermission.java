package com.example.socapplication.model.dto.adminPermissionDto;

import java.time.OffsetDateTime;

public record ResponseAdminPermission(
        Integer id,
        Long userId,
        Integer permissionId,
        String permissionName,
        OffsetDateTime grantedAt,
        Long grantedBy
) {}