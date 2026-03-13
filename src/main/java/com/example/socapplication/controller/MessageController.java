package com.example.socapplication.controller;

import com.example.socapplication.model.dto.messageDto.SendMessage;
import com.example.socapplication.model.dto.messageDto.ResponseMessage;
import com.example.socapplication.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations/{conversationId}/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<ResponseMessage> getMessages(@PathVariable Long conversationId) {
        return messageService.findMessagesByConversationId(conversationId);
    }

    @PostMapping
    public ResponseMessage sendMessage(
            @PathVariable Long conversationId,
            @RequestBody SendMessage dto
    ) {
        return messageService.sendMessage(conversationId, dto);
    }
}