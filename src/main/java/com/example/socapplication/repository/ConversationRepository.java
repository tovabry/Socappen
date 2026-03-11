package com.example.socapplication.repository;

import com.example.socapplication.model.entity.Conversation;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends ListCrudRepository<Conversation, Long> {
}
