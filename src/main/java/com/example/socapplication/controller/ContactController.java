package com.example.socapplication.controller;

import com.example.socapplication.model.dto.contactDto.CreateContact;
import com.example.socapplication.model.dto.contactDto.ResponseContact;
import com.example.socapplication.model.dto.contactDto.UpdateContact;
import com.example.socapplication.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseContact>> findAll(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(contactService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseContact> findById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResponseContact>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(contactService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Void> createContact(@RequestBody CreateContact dto) {
        contactService.createContact(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateContact(@PathVariable Long id, @RequestBody UpdateContact dto) {
    contactService.updateContact(id, dto);
    return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
    contactService.deleteContact(id);
    return ResponseEntity.noContent().build();
    }




}
