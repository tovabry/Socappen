package com.example.socapplication.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class ConversationParticipantId implements Serializable {

    @Column(name = "conversation_id")
    private Long conversationId;

    @Column(name = "app_user_id")
    private Long appUserId;

    public ConversationParticipantId() {}

    public ConversationParticipantId(Long conversationId, Long appUserId){
        this.conversationId = conversationId;
        this.appUserId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConversationParticipantId that)) return false;
        return conversationId.equals(that.conversationId)
                && appUserId.equals(that.appUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationId, appUserId);
    }
}
