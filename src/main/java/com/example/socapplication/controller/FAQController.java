package com.example.socapplication.controller;

import com.example.socapplication.model.dto.faqDto.ResponseFaq;
import com.example.socapplication.service.FAQService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
public class FAQController {

    FAQService faqService;

    public FAQController(FAQService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/{id}")
    public ResponseFaq getFaqById(@PathVariable Long id) {return faqService.findFaqById(id);}

    @GetMapping
    public List<ResponseFaq> getAllFaqs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return faqService.findAll(page, size);
    }
}
