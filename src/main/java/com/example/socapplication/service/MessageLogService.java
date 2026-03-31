package com.example.socapplication.service;

import com.example.socapplication.model.dto.messageLogDto.CreateMessageLog;
import com.example.socapplication.model.dto.messageLogDto.ResponseMessageLog;
import com.example.socapplication.model.entity.MessageLog;
import com.example.socapplication.repository.MessageLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageLogService {
    private final MessageLogRepository repository;

    public void log(CreateMessageLog dto) {
        repository.save(MessageLog.builder()
                .appUserId(dto.appUserId())
                .conversationId(dto.conversationId())
                .ipAddress(dto.ipAddress())
                .createdAt(OffsetDateTime.now())
                .build());
    }

    public List<ResponseMessageLog> findAll() {
        return repository.findAll().stream()
                .map(l -> new ResponseMessageLog(
                        l.getId(),
                        l.getAppUserId(),
                        l.getConversationId(),
                        l.getIpAddress(),
                        l.getCreatedAt()))
                .toList();
    }
}
