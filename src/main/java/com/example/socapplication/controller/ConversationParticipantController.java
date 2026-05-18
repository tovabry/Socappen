package com.example.socapplication.controller;

import com.example.socapplication.model.dto.participantDto.AddParticipant;
import com.example.socapplication.model.dto.participantDto.ResponseParticipant;
import com.example.socapplication.service.ConversationParticipantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations/{conversationId}/participants")
public class ConversationParticipantController {

    private final ConversationParticipantService participantService;

    public ConversationParticipantController(ConversationParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public List<ResponseParticipant> getParticipants(@PathVariable Long conversationId) {
        return participantService.findParticipantsByConversationId(conversationId);
    }

    @PostMapping
    public ResponseParticipant addParticipant(
            @PathVariable Long conversationId,
            @RequestBody AddParticipant dto
    ) {
        return participantService.addParticipant(conversationId, dto);
    }
}