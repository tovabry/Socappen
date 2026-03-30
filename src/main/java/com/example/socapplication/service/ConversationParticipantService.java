package com.example.socapplication.service;

import com.example.socapplication.model.dto.participantDto.AddParticipant;
import com.example.socapplication.model.dto.participantDto.ResponseParticipant;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Conversation;
import com.example.socapplication.model.entity.ConversationParticipant;
import com.example.socapplication.model.entity.ConversationParticipantId;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.repository.ConversationParticipantRepository;
import com.example.socapplication.repository.ConversationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class ConversationParticipantService {

    private final ConversationParticipantRepository conversationParticipantRepository;
    private final ConversationRepository conversationRepository;
    private final AppUserRepository appUserRepository;

    public ConversationParticipantService(ConversationParticipantRepository conversationParticipantRepository, ConversationRepository conversationRepository, AppUserRepository appUserRepository) {
        this.conversationParticipantRepository = conversationParticipantRepository;
        this.conversationRepository = conversationRepository;
        this.appUserRepository = appUserRepository;

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

    public ResponseParticipant addParticipant(Long conversationId, AddParticipant dto) {
        // 1. Fetch Conversation
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

        // 2. Fetch AppUser
        AppUser appUser = appUserRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 3. Create ConversationParticipant entity
        ConversationParticipant participant = new ConversationParticipant();
        participant.setConversation(conversation);
        participant.setAppUser(appUser);
        participant.setJoinedAt(OffsetDateTime.now());

        // 4. Set EmbeddedId
        participant.setId(new ConversationParticipantId(conversation.getId(), appUser.getId()));

        // 5. Save to repository
        conversationParticipantRepository.save(participant);

        // 6. Return DTO
        return new ResponseParticipant(
                conversation.getId(),
                appUser.getId(),
                participant.getJoinedAt()
        );
    }
}