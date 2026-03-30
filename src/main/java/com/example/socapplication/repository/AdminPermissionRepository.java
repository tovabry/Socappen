package com.example.socapplication.repository;

import com.example.socapplication.model.entity.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPermissionRepository extends JpaRepository<AdminPermission, Integer> {
    List<AdminPermission> findByAppUser_Id(Long userId);
    void deleteByAppUser_IdAndPermission_Id(Long userId, Integer permissionId);
}