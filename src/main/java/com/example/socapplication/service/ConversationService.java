package com.example.socapplication.service;

import com.example.socapplication.model.dto.conversationDto.ResponseConversation;
import com.example.socapplication.model.entity.Conversation;
import com.example.socapplication.repository.ConversationRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
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

}