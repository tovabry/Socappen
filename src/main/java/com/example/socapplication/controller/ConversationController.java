package com.example.socapplication.controller;

import com.example.socapplication.model.dto.conversationDto.ResponseConversation;
import com.example.socapplication.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping()
    public List<ResponseConversation> getAllConversations() {
        return conversationService.findAllConversations();
    }

    @GetMapping("/{id}")
    public ResponseConversation getConversation(@PathVariable Long id) {
        return conversationService.findConversationById(id);
    }
}