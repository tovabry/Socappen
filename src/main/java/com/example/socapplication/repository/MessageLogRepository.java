package com.example.socapplication.repository;

import com.example.socapplication.model.entity.MessageLog;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageLogRepository extends ListCrudRepository<MessageLog, Long> {}

