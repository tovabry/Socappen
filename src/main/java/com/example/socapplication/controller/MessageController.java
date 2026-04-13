package com.example.socapplication.controller;

import com.example.socapplication.model.dto.messageDto.SendMessage;
import com.example.socapplication.model.dto.messageDto.ResponseMessage;
import com.example.socapplication.model.dto.messageLogDto.CreateMessageLog;
import com.example.socapplication.service.MessageLogService;
import com.example.socapplication.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations/{conversationId}/messages")
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageLogService messageLogService;

    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate,  MessageLogService messageLogService) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
        this.messageLogService = messageLogService;
    }

    @GetMapping
    public List<ResponseMessage> getMessages(@PathVariable Long conversationId, @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        return messageService.findMessagesByConversationId(conversationId, page, size);
    }

    @PostMapping
    public ResponseMessage sendMessage(
            @PathVariable Long conversationId,
            @RequestBody SendMessage dto,
            Authentication authentication,
            HttpServletRequest httpRequest) {

        String email = authentication.getName();
        String ip = getClientIp(httpRequest);

        ResponseMessage response = messageService.sendMessage(conversationId, email, dto.content());

        messageLogService.log(new CreateMessageLog(response.senderId(), conversationId, ip));

        messagingTemplate.convertAndSend("/conversation/" + conversationId, response);

        return response;
    }

    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}