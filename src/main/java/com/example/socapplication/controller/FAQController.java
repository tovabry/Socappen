package com.example.socapplication.controller;

import com.example.socapplication.model.dto.faqDto.ResponseFaq;
import com.example.socapplication.service.CurrentUser;
import com.example.socapplication.service.FAQService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/faq")
public class FAQController {

    FAQService faqService;

    public FAQController(FAQService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/all")
    public List<ResponseFaq> getAllFaqs(){
        return faqService.findAll();
    }
}
