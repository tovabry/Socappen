package com.example.socapplication.service;

import com.example.socapplication.model.dto.messageDto.ResponseMessage;
import com.example.socapplication.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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

}