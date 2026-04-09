package com.example.socapplication.repository;

import com.example.socapplication.model.entity.Conversation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c JOIN ConversationParticipant cp ON cp.conversation.id = c.id WHERE cp.appUser.id = :userId")
    List<Conversation> findByCurrentUser(@Param("userId") Long userId, Pageable pageable);
}