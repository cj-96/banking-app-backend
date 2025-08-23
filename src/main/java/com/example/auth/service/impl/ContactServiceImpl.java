package com.example.auth.service.impl;

import com.example.auth.exceptionhandling.ContactException;
import com.example.auth.exceptionhandling.InternalServerException;
import com.example.auth.model.Contact;
import com.example.auth.repository.ContactRepository;
import com.example.auth.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public Contact persist(Contact contact) {
        log.debug("Attempting to persist contact request for customer: {}", contact.getContactEmail());

        try {

            // Assign Service Request Number & Creation Date
            contact.setContactId(getServiceReqNumber());
            contact.setCreateDt(new Date(System.currentTimeMillis()));

            Contact savedContact = contactRepository.save(contact);
            log.debug("Successfully persisted contact with Service Request Number: {}", savedContact.getContactId());

            return savedContact;

        } catch (ContactException e) {
            // Re-throw business exception
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while persisting contact for customer: {}",
                    contact.getContactEmail(), e);
            throw new InternalServerException(
                    String.format("Unexpected error occurred while persisting contact for customer: %s",
                            contact.getContactEmail())
            );
        }
    }

    private String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + ranNum;
    }
}
