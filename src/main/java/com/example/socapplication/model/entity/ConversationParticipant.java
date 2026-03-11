package com.example.socapplication.model.entity;

import com.example.socapplication.model.entity.AppUser;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

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

    public void setId(ConversationParticipantId id) {
        this.id = id;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public ConversationParticipantId getId() {
        return id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public OffsetDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(OffsetDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }


}

