package com.example.socapplication.repository;

import com.example.socapplication.model.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {


    List<Message> findByConversation_Id(Long conversationId, Pageable pageable);
}
