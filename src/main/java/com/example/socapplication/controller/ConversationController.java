package com.example.socapplication.controller;

import com.example.socapplication.model.dto.conversationDto.ResponseConversation;
import com.example.socapplication.service.ConversationService;
import com.example.socapplication.service.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final CurrentUser currentUser;

    public ConversationController(ConversationService conversationService, CurrentUser currentUser) {
        this.conversationService = conversationService;
        this.currentUser = currentUser;
    }

    @GetMapping()
    public List<ResponseConversation> getAllConversations() {
        return conversationService.findAllConversations();
    }

    @GetMapping("/{id}")
    public ResponseConversation getConversation(@PathVariable Long id) {
        return conversationService.findConversationById(id);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ResponseConversation>> getConversationsBasedOnCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Long userId = currentUser.getUserId();
        List<ResponseConversation> conversations = conversationService.findConversationBasedOnCurrentUser(userId, page, size);
        return ResponseEntity.ok(conversations);
    }
}