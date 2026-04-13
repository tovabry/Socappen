package com.example.socapplication.service;

import com.example.socapplication.model.dto.conversationDto.ResponseConversation;
import com.example.socapplication.model.dto.participantDto.AddParticipant;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Conversation;
import com.example.socapplication.model.entity.ConversationParticipant;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.repository.ConversationRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.example.socapplication.enums.user.ConversationStatus.active;

@Service
@Transactional
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final AppUserRepository appUserRepository;

    public ConversationService(ConversationRepository conversationRepository, AppUserRepository appUserRepository) {
        this.conversationRepository = conversationRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<ResponseConversation> findAllConversations() {
        return conversationRepository.findAll()
                .stream()
                .map(conversation -> new ResponseConversation(
                        conversation.getId(),
                        conversation.getStatus(),
                        conversation.getCreatedAt(),
                        conversation.getLastActivityAt()
                ))
                .toList();
    }

    public ResponseConversation findConversationById(Long id) {

        var conversation = conversationRepository.findById(id)
                .orElseThrow();

        return new ResponseConversation(
                conversation.getId(),
                conversation.getStatus(),
                conversation.getCreatedAt(),
                conversation.getLastActivityAt()
        );
    }

    public List<ResponseConversation> findConversationBasedOnCurrentUser(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return conversationRepository.findByCurrentUser(id, pageable)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ResponseConversation toResponse(Conversation conversation) {
        return new ResponseConversation(
                conversation.getId(),
                conversation.getStatus(),
                conversation.getCreatedAt(),
                conversation.getLastActivityAt()
        );
    }

    public ResponseConversation createConversation(AddParticipant dto) {
        AppUser user = appUserRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Conversation conversation = new Conversation();
        conversation.setStatus(active);
        Conversation savedConversation = conversationRepository.save(conversation);

        ConversationParticipant participant = new ConversationParticipant();
        participant.setAppUser(user);
        participant.setConversation(new Conversation());
        savedConversation.getParticipants().add(participant);

        conversationRepository.save(savedConversation);
        return toResponse(savedConversation);
    }



}