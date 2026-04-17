package com.example.socapplication.service;

import com.example.socapplication.model.dto.messageDto.ResponseMessage;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Conversation;
import com.example.socapplication.model.entity.Message;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.repository.ConversationRepository;
import com.example.socapplication.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<ResponseMessage> findMessagesByConversationId(Long conversationId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("sentAt").descending());
        return messageRepository.findByConversation_Id(conversationId, pageable)
                .stream()
                .map(message -> new ResponseMessage(
                        message.getId(),
                        message.getSender().getId(),
                        message.getContent(),
                        message.getSentAt()
                ))
                .toList();
    }

    public ResponseMessage sendMessage(Long conversationId, String email, String content) {

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));


        AppUser sender = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(content);
        message.setSentAt(OffsetDateTime.now());


        messageRepository.save(message);


        return new ResponseMessage(
                message.getId(),
                sender.getId(),
                message.getContent(),
                message.getSentAt()
        );
    }

}