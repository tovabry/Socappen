package com.example.socapplication.service;

import com.example.socapplication.model.dto.faqDto.CreateFaq;
import com.example.socapplication.model.dto.faqDto.ResponseFaq;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.FrequentlyAskedQuestion;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.repository.FAQRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;


@Service
@Transactional
public class FAQService {

    private final FAQRepository faqRepository;
    private final AppUserRepository appUserRepository;

    public FAQService(FAQRepository FAQRepository, AppUserRepository appUserRepository) {
        this.faqRepository = FAQRepository;
        this.appUserRepository = appUserRepository;
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

    public FrequentlyAskedQuestion createFaq(CreateFaq dto) {
        AppUser user = appUserRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        FrequentlyAskedQuestion faq = new FrequentlyAskedQuestion();
        faq.setAppUserId(user);
        faq.setQuestion(dto.question());
        faq.setAnswer(dto.Answer());
        return faqRepository.save(faq);
    }

    public void updateFaq(Long id, CreateFaq dto) {
        FrequentlyAskedQuestion faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        faq.setQuestion(dto.question());
        faq.setAnswer(dto.Answer());
        faqRepository.save(faq);
    }

    public void deleteFaq(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        faqRepository.deleteById(id);
    }

}
