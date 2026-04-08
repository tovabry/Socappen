package com.example.socapplication.repository;

import com.example.socapplication.model.entity.FrequentlyAskedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FAQRepository extends JpaRepository<FrequentlyAskedQuestion, Long> {
}