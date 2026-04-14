package com.example.socapplication.service;

import com.example.socapplication.model.dto.adminPermissionDto.AddAdminPermission;
import com.example.socapplication.model.dto.adminPermissionDto.ResponseAdminPermission;
import com.example.socapplication.model.dto.permissionDto.ResponsePermission;
import com.example.socapplication.model.entity.AdminPermission;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Permission;
import com.example.socapplication.model.entity.Role;
import com.example.socapplication.repository.AdminPermissionRepository;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.repository.PermissionRepository;
import com.example.socapplication.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class AdminPermissionService {

    private final AdminPermissionRepository adminPermissionRepository;
    private final AppUserRepository appUserRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public AdminPermissionService(AdminPermissionRepository adminPermissionRepository,
                                  AppUserRepository appUserRepository,
                                  PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.adminPermissionRepository = adminPermissionRepository;
        this.appUserRepository = appUserRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    public List<ResponsePermission> findAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(p -> new ResponsePermission(p.getId(), p.getName()))
                .toList();
    }

    public List<ResponseAdminPermission> findByUserId(Long userId) {
        return adminPermissionRepository.findByAppUser_Id(userId)
                .stream()
                .map(p -> new ResponseAdminPermission(
                        p.getId(),
                        p.getAppUser().getId(),
                        p.getPermission().getId(),
                        p.getPermission().getName(),
                        p.getGrantedAt(),
                        p.getGrantedBy().getId()
                ))
                .toList();
    }

    public void grantPermission(AddAdminPermission dto, Long grantedById) {
        AppUser user = appUserRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AppUser grantedBy = appUserRepository.findById(grantedById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Permission permission = permissionRepository.findById(dto.permissionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!user.getRole().getName().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not an admin");
        }

        AdminPermission adminPermission = new AdminPermission();
        adminPermission.setAppUser(user);
        adminPermission.setPermission(permission);
        adminPermission.setGrantedAt(OffsetDateTime.now());
        adminPermission.setGrantedBy(grantedBy);

        adminPermissionRepository.save(adminPermission);
    }

    public void revokePermission(Long userId, Integer permissionId) {
        adminPermissionRepository.deleteByAppUser_IdAndPermission_Id(userId, permissionId);
    }

    public void promoteToAdmin(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Role adminRole = roleRepository.findByName("admin")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found"));

        user.setRole(adminRole);
        appUserRepository.save(user);
    }

    public void demoteToUser(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Role userRole = roleRepository.findByName("user")
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found"));

        user.setRole(userRole);
        adminPermissionRepository.deleteAll(adminPermissionRepository.findByAppUser_Id(userId));
        appUserRepository.save(user);
    }
}