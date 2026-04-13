package com.example.socapplication.service;

import com.example.socapplication.model.dto.postLogDto.CreatePostLog;
import com.example.socapplication.model.dto.postLogDto.ResponsePostLog;
import com.example.socapplication.model.entity.PostLog;
import com.example.socapplication.repository.PostLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLogService {
    private final PostLogRepository repository;

    public void log(CreatePostLog dto) {
        repository.save(PostLog.builder()
                .appUserId(dto.appUserId())
                .postId(dto.postId())
                .ipAddress(dto.ipAddress())
                .createdAt(dto.createdAt())
                .build());
    }

    public List<ResponsePostLog> findAll() {
        return repository.findAll().stream()
                .map(l -> new ResponsePostLog(
                        l.getId(),
                        l.getAppUserId(),
                        l.getPostId(),
                        l.getIpAddress(),
                        l.getCreatedAt()))
                .toList();
    }




}
