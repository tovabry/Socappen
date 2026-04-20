package com.example.socapplication.controller;

import com.example.socapplication.model.dto.conversationDto.CreateConversation;
import com.example.socapplication.model.dto.conversationDto.ResponseConversation;
import com.example.socapplication.service.ConversationService;
import com.example.socapplication.service.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ResponseConversation>> getAllConversations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Long userId = currentUser.getUserId();
        List<ResponseConversation> conversations = conversationService.findAllConversations(userId, page, size);
        return ResponseEntity.ok(conversations);
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

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseConversation> createConversation(
            @RequestBody CreateConversation dto) {

        ResponseConversation response = conversationService.createConversation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{conversationId}/join")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseConversation> joinConversation(@PathVariable Long conversationId) {
        Long adminId = currentUser.getUserId();
        ResponseConversation response = conversationService.joinConversation(conversationId, adminId);
        return ResponseEntity.ok(response);
    }
}