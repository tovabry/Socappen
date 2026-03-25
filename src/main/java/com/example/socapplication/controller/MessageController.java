package com.example.socapplication.controller;

import com.example.socapplication.model.dto.messageDto.SendMessage;
import com.example.socapplication.model.dto.messageDto.ResponseMessage;
import com.example.socapplication.service.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations/{conversationId}/messages")
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<ResponseMessage> getMessages(@PathVariable Long conversationId) {
        return messageService.findMessagesByConversationId(conversationId);
    }

    @PostMapping
    public ResponseMessage sendMessage(
            @PathVariable Long conversationId,
            @RequestBody SendMessage dto,
            Authentication authentication) {
        String email = authentication.getName();
        ResponseMessage response = messageService.sendMessage(conversationId, email, dto.content());

        messagingTemplate.convertAndSend("/conversation/" + conversationId, response);

        return response;
    }
}