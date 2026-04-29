package com.example.socapplication.service;

import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.repository.AdminPermissionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionService {
    private final AdminPermissionRepository adminPermissionRepository;

    public PermissionService(AdminPermissionRepository adminPermissionRepository) {
        this.adminPermissionRepository = adminPermissionRepository;
    }

    public boolean hasPermission(Authentication authentication, String permissionName){
        AppUser user = (AppUser) authentication.getPrincipal();
        return adminPermissionRepository.findByAppUser_Id(user.getId())
                .stream()
                .anyMatch(p -> p.getPermission().getName().equals(permissionName));
    }


}
