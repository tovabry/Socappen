package com.example.socapplication.service;

import com.example.socapplication.model.dto.participantDto.ResponseParticipant;
import com.example.socapplication.repository.ConversationParticipantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ConversationParticipantService {

    private final ConversationParticipantRepository conversationParticipantRepository;

    public ConversationParticipantService(ConversationParticipantRepository conversationParticipantRepository) {
        this.conversationParticipantRepository = conversationParticipantRepository;
    }

    public List<ResponseParticipant> findParticipantsByConversationId(Long conversationId) {

        return conversationParticipantRepository.findConversationById(conversationId)
                .stream()
                .map(participant -> new ResponseParticipant(
                        participant.getConversation().getId(),
                        participant.getAppUser().getId(),
                        participant.getJoinedAt()
                ))
                .toList();
    }
}