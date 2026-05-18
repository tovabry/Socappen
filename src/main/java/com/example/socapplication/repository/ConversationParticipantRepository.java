package com.example.socapplication.repository;

import com.example.socapplication.model.entity.ConversationParticipant;
import com.example.socapplication.model.entity.ConversationParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, ConversationParticipantId> {

    //Spring automatically find by id, thanks to the name. Same on the others.
    List<ConversationParticipant> findById_ConversationId(Long conversationId);

    //Counts the participants in a conversation
    long countById_ConversationId(Long conversationId);

    boolean existsByConversation_IdAndAppUser_Id(Long conversationId, Long appUserId);
}