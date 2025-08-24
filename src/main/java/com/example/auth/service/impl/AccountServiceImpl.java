package com.example.auth.service.impl;

import com.example.auth.exceptionhandling.AccountException;
import com.example.auth.exceptionhandling.InternalServerException;
import com.example.auth.exceptionhandling.InvalidCustomerIdException;
import com.example.auth.model.Accounts;
import com.example.auth.repository.AccountsRepository;
import com.example.auth.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountsRepository accountsRepository;

    @Override
    public Accounts retriveByCustomerId(long id) {
        log.debug("Attempting to retrieve account for customer ID: {}", id);

        // Input validation
        validateCustomerId(id);

        try {
            Optional<Accounts> accountOptional = Optional.ofNullable(accountsRepository.findByCustomerId(id));

            if (accountOptional.isEmpty()) {
                log.warn("No account found for customer ID: {}", id);
                throw new AccountException(
                        String.format("Account not found for customer ID: %d", id)
                );
            }

            Accounts account = accountOptional.get();
            log.debug("Successfully retrieved account for customer ID: {}", id);
            return account;

        } catch (AccountException e) {
            // Re-throw business exceptions
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving account for customer ID: {}",
                    id, e);
            throw new InternalServerException(
                    String.format("Unexpected error occurred while retrieving account for customer ID: %d", id)
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
