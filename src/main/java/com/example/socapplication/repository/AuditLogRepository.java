package com.example.socapplication.repository;

import com.example.socapplication.model.entity.AuditLog;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends ListCrudRepository<AuditLog, Long> {}
