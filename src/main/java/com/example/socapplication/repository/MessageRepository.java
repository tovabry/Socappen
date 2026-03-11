package com.example.socapplication.repository;

import com.example.socapplication.model.entity.ConversationParticipant;
import com.example.socapplication.model.entity.Message;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MessageRepository extends ListCrudRepository<Message, Long> {

    List<Message> findByConversation_Id(Long conversationId);
}
