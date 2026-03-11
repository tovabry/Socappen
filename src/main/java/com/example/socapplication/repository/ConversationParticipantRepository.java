package com.example.socapplication.repository;

import com.example.socapplication.model.dto.QandADto.ResponseQAndA;
import com.example.socapplication.model.entity.ConversationParticipant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationParticipantRepository extends ListCrudRepository<ConversationParticipant, Long> {

    List<ConversationParticipant> findConversationById(Long conversationId);}
