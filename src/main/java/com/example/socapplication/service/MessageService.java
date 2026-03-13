package com.example.socapplication.service;

import com.example.socapplication.model.dto.messageDto.ResponseMessage;
import com.example.socapplication.model.dto.messageDto.SendMessage;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Conversation;
import com.example.socapplication.model.entity.Message;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.repository.ConversationRepository;
import com.example.socapplication.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final AppUserRepository appUserRepository;

    public MessageService(MessageRepository messageRepository,  ConversationRepository conversationRepository, AppUserRepository appUserRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<ResponseMessage> findMessagesByConversationId(Long conversationId) {

        return messageRepository.findByConversation_Id(conversationId)
                .stream()
                .map(message -> new ResponseMessage(
                        message.getId(),
                        message.getSender().getId(),
                        message.getContent(),
                        message.getSentAt()
                ))
                .toList();
    }

    public ResponseMessage sendMessage(Long conversationId, SendMessage dto) {
        // 1. Fetch Conversation
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

        // 2. Fetch sender (AppUser)
        AppUser sender = appUserRepository.findById(Math.toIntExact(dto.senderId()))
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        // 3. Create Message entity
        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(dto.content());
        message.setSentAt(OffsetDateTime.now());

        // 4. Save to repository
        messageRepository.save(message);

        // 5. Return DTO
        return new ResponseMessage(
                message.getId(),
                sender.getId(),
                message.getContent(),
                message.getSentAt()
        );
    }



}