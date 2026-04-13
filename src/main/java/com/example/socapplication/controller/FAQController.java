package com.example.socapplication.controller;

import com.example.socapplication.model.dto.faqDto.CreateFaq;
import com.example.socapplication.model.dto.faqDto.ResponseFaq;
import com.example.socapplication.model.dto.faqLogDto.CreateFaqLog;
import com.example.socapplication.model.entity.FrequentlyAskedQuestion;
import com.example.socapplication.service.FAQService;
import com.example.socapplication.service.FaqLogServie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/faq")
public class FAQController {

    private final FaqLogServie faqLogServie;
    FAQService faqService;

    public FAQController(FAQService faqService, FaqLogServie faqLogServie) {
        this.faqService = faqService;
        this.faqLogServie = faqLogServie;
    }

    @GetMapping("/{id}")
    public ResponseFaq getFaqById(@PathVariable Long id) {return faqService.findFaqById(id);}

    @GetMapping
    public List<ResponseFaq> getAllFaqs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return faqService.findAll(page, size);
    }

    @PostMapping
    public ResponseEntity<Void> createFaq(@RequestBody CreateFaq dto, HttpServletRequest request){
        FrequentlyAskedQuestion faq = faqService.createFaq(dto);

        String ip = getClientIp(request);

        faqLogServie.log(new CreateFaqLog(
                dto.userId(),
                faq.getId(),
                ip,
                OffsetDateTime.now()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFaq(@PathVariable Long id, @RequestBody CreateFaq dto) {
        faqService.updateFaq(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
        return ResponseEntity.noContent().build();
    }

    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
