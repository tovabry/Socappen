package com.example.socapplication.service;

import com.example.socapplication.model.dto.faqDto.ResponseFaq;
import com.example.socapplication.repository.FAQRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@Transactional
public class FAQService {

    private final FAQRepository faqRepository;

    public FAQService(FAQRepository FAQRepository) {
        this.faqRepository = FAQRepository;
    }

    public ResponseFaq findFaqById(long id) {
        return faqRepository.findById(id)
                .map(faq -> new ResponseFaq(faq.getId(), faq.getQuestion(), faq.getAnswer()))
                .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + id));
    }

    public List<ResponseFaq> findAll(int page, int size) {
        System.out.println("PAGE: " + page + " SIZE: " + size);
        Pageable pageable = PageRequest.of(page, size);
        return faqRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(faq -> new ResponseFaq(
                        faq.getId(),
                        faq.getQuestion(),
                        faq.getAnswer()
                ))
                .toList();
    }
}
