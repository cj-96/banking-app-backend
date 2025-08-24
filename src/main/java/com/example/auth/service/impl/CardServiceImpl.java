package com.example.auth.service.impl;

import com.example.auth.exceptionhandling.CardException;
import com.example.auth.exceptionhandling.InternalServerException;
import com.example.auth.exceptionhandling.InvalidCustomerIdException;
import com.example.auth.model.Cards;
import com.example.auth.repository.CardsRepository;
import com.example.auth.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    private final CardsRepository cardsRepository;

    @Override
    public List<Cards> retrieveByCustomerId(long id) {
        log.debug("Attempting to retrieve cards for customer ID: {}", id);

        // Input validation
        validateCustomerId(id);

        try {
            List<Cards> cards = cardsRepository.findByCustomerId(id);

            if (cards == null || cards.isEmpty()) {
                log.warn("No cards found for customer ID: {}", id);
                throw new CardException(
                        String.format("No cards found for customer ID: %d", id)
                );
            }

            log.debug("Successfully retrieved {} cards for customer ID: {}", cards.size(), id);
            return cards;

        } catch (CardException e) {
            // Re-throw business exceptions
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving cards for customer ID: {}", id, e);
            throw new InternalServerException(
                    String.format("Unexpected error occurred while retrieving cards for customer ID: %d", id)
            );
        }
    }

    private void validateCustomerId(Long customerId) {
        if (customerId == null) {
            log.warn("Customer ID validation failed: null value provided");
            throw new InvalidCustomerIdException("Customer ID cannot be null");
        }

        if (customerId <= 0) {
            log.warn("Customer ID validation failed: non-positive value provided: {}", customerId);
            throw new InvalidCustomerIdException(
                    String.format("Customer ID must be positive, but was: %d", customerId)
            );
        }
    }
}
