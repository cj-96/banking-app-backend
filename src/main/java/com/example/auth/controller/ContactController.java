package com.example.auth.controller;

import com.example.auth.model.Contact;
import com.example.auth.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/contact")
    public Contact persist(@RequestBody Contact contact) {
        return contactService.persist(contact);
    }
}


