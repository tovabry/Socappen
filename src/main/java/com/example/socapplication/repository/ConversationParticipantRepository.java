package com.example.socapplication.repository;

import com.example.socapplication.model.entity.ConversationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, Long> {

    List<ConversationParticipant> findConversationById(Long conversationId);

    boolean existsByConversation_IdAndAppUser_Id(Long conversationId, Long appUserId);
}