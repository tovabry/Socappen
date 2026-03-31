package com.example.socapplication.service;

import com.example.socapplication.model.dto.authLogDto.CreateAuthLog;
import com.example.socapplication.model.dto.authLogDto.ResponseAuthLog;
import com.example.socapplication.model.entity.AuthLog;
import com.example.socapplication.repository.AuthLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthLogService {
    private final AuthLogRepository repository;

    public List<ResponseAuthLog> findAll() {
        return repository.findAll().stream()
                .map(l -> new ResponseAuthLog(
                        l.getId(),
                        l.getEmail(),
                        l.getIpAddress(),
                        l.isSuccess(),
                        l.getFailReason(),
                        l.getLoggedInAt(),
                        l.getLoggedOutAt(),
                        l.getCreatedAt()))
                .toList();
    }

    public void logLogin(CreateAuthLog dto) {
        repository.save(AuthLog.builder()
                .email(dto.email())
                .ipAddress(dto.ipAddress())
                .success(dto.success())
                .failReason(dto.failReason())
                .createdAt(OffsetDateTime.now())
                .loggedInAt(dto.success() ? OffsetDateTime.now() : null)
                .build());
    }

    public void logLogout(String email) {
        repository.findTopByEmailAndLoggedOutAtIsNullOrderByLoggedInAtDesc(email)
                .ifPresent(log -> {
                    log.setLoggedOutAt(OffsetDateTime.now());
                    repository.save(log);
                });
    }
}
