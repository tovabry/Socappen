package com.example.socapplication.service;

import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.model.dto.faqLogDto.CreateFaqLog;
import com.example.socapplication.model.dto.faqLogDto.ResponseFaqLog;
import com.example.socapplication.model.entity.FaqLog;
import com.example.socapplication.repository.FaqLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FaqLogServie {
    private final FaqLogRepository repository;

    public void log(CreateFaqLog dto) {
        repository.save(FaqLog.builder()
                .appUserId(dto.appUserId())
                .faqId(dto.faqId())
                .ipAddress(dto.ipAddress())
                .createdAt(dto.createdAt())
                .build());
    }

    public List<ResponseFaqLog> findAll() {
        return repository.findAll().stream()
                .map(l -> new ResponseFaqLog(
                        l.getId(),
                        l.getAppUserId(),
                        l.getFaqId(),
                        l.getIpAddress(),
                        l.getCreatedAt()))
                .toList();
    }
}
