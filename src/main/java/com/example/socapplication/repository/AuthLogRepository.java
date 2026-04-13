package com.example.socapplication.repository;

import com.example.socapplication.model.entity.AuthLog;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthLogRepository extends ListCrudRepository<AuthLog, Long> {
    Optional<AuthLog> findTopByAppUserIdAndLoggedOutAtIsNullOrderByLoggedInAtDesc(Long userId);
}