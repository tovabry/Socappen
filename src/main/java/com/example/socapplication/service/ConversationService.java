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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
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

    public List<ResponseConversation> findAllConversations(Long requesterId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication != null &&
                authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ROLE_SYSADMIN".equals(a.getAuthority()));

        if (isAdmin) {
            return conversationRepository.findAll(pageable)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        } else {
            return conversationRepository.findByCurrentUser(requesterId, pageable)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isUser = authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> "ROLE_USER".equals(a.getAuthority()));

        if (!isUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only users can create conversations");
        }

        AppUser user = appUserRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Conversation conversation = new Conversation();
        conversation.setStatus(active);
        conversation.setCreatedAt(OffsetDateTime.now());
        conversation.setLastActivityAt(OffsetDateTime.now());
        Conversation savedConversation = conversationRepository.save(conversation);

        ConversationParticipant participant = new ConversationParticipant();
        participant.setAppUser(user);
        participant.setConversation(savedConversation);
        savedConversation.getParticipants().add(participant);

        conversationRepository.save(savedConversation);
        return toResponse(savedConversation);
    }

    public ResponseConversation joinConversation(Long conversationId, Long adminId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ROLE_SYSADMIN".equals(a.getAuthority()));

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can join existing conversations");
        }

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation not found"));

        boolean alreadyParticipant = conversation.getParticipants().stream()
                .anyMatch(p -> p.getAppUser().getId().equals(adminId));

        if (alreadyParticipant) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin is already in this conversation");
        }

        AppUser admin = appUserRepository.findById(adminId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ConversationParticipant participant = new ConversationParticipant();
        participant.setAppUser(admin);
        participant.setConversation(conversation);
        conversation.getParticipants().add(participant);

        conversationRepository.save(conversation);
        return toResponse(conversation);
    }
}