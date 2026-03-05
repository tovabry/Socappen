package com.example.socapplication.repository;

import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.FrequentlyAskedQuestion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends ListCrudRepository<FrequentlyAskedQuestion, Long> {

}