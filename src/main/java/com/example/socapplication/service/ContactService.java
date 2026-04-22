package com.example.socapplication.service;

import com.example.socapplication.model.dto.contactDto.CreateContact;
import com.example.socapplication.model.dto.contactDto.ResponseContact;
import com.example.socapplication.model.dto.contactDto.UpdateContact;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Contact;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.repository.ContactRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;
    private final AppUserRepository appUserRepository;

    public ContactService(ContactRepository contactRepository, AppUserRepository appUserRepository) {
        this.contactRepository = contactRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<ResponseContact> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contactRepository.findAll(pageable)
                .stream()
                .map(contact -> new ResponseContact(
                        contact.getId(),
                        contact.getAppUserId().getId(),
                        contact.getTitle(),
                        contact.getUrl(),
                        contact.getMail(),
                        contact.getPhone(),
                        contact.getCreatedAt(),
                        contact.getUpdatedAt()
                ))
                .toList();
    }

    public ResponseContact findById(Long id) {
        return contactRepository.findById(id)
                .map(contact -> new ResponseContact(
                        contact.getId(),
                        contact.getAppUserId().getId(),
                        contact.getTitle(),
                        contact.getUrl(),
                        contact.getMail(),
                        contact.getPhone(),
                        contact.getCreatedAt(),
                        contact.getUpdatedAt()
                ))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<ResponseContact> findByUserId(Long userId) {
        if (!appUserRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return contactRepository.findByAppUserId_Id(userId)
                .stream()
                .map(contact -> new ResponseContact(
                        contact.getId(),
                        contact.getAppUserId().getId(),
                        contact.getTitle(),
                        contact.getUrl(),
                        contact.getMail(),
                        contact.getPhone(),
                        contact.getCreatedAt(),
                        contact.getUpdatedAt()
                ))
                .toList();
    }

    public void createContact(CreateContact dto) {
        AppUser user = appUserRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Contact contact = new Contact();
        contact.setAppUserId(user);
        contact.setTitle(dto.title());
        contact.setUrl(dto.img_url());
        contact.setMail(dto.mail());
        contact.setPhone(dto.phone());
        contactRepository.save(contact);
    }

    public void updateContact(Long id, UpdateContact dto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        contact.setTitle(dto.title());
        contact.setUrl(dto.img_url());
        contact.setMail(dto.mail());
        contact.setPhone(dto.phone());

        contactRepository.save(contact);
    }

    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        contactRepository.deleteById(id);
    }

}

