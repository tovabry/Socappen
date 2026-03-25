package com.example.socapplication.model.entity;

import com.example.socapplication.model.entity.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "conversation_participant", schema = "dbo")
public class ConversationParticipant {

    @EmbeddedId
    private ConversationParticipantId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("conversationId")
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("appUserId")
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Column(name = "joined_at", nullable = false)
    private OffsetDateTime joinedAt;

    public ConversationParticipant() {}

    public ConversationParticipant(Conversation conversation, AppUser appUser) {
        this.conversation = conversation;
        this.appUser = appUser;
        this.id = new ConversationParticipantId(conversation.getId(), appUser.getId());
    }


}

