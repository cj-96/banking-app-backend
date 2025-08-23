package com.example.auth.service.impl;

import com.example.auth.exceptionhandling.BalanceException;
import com.example.auth.exceptionhandling.InternalServerException;
import com.example.auth.exceptionhandling.InvalidCustomerIdException;
import com.example.auth.model.AccountTransactions;
import com.example.auth.repository.AccountTransactionsRepository;
import com.example.auth.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BalanceServiceImpl implements BalanceService {

    private final AccountTransactionsRepository accountTransactionsRepository;

    @Override
    public List<AccountTransactions> retreiveByCustomerId(long id) {
        log.debug("Attempting to retrieve account transactions for customer ID: {}", id);

        // Input validation
        validateCustomerId(id);

        try {
            List<AccountTransactions> accountTransactions =
                    accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id);

            if (accountTransactions == null || accountTransactions.isEmpty()) {
                log.warn("No transactions found for customer ID: {}", id);
                throw new BalanceException(
                        String.format("No transactions found for customer ID: %d", id)
                );
            }

            log.debug("Successfully retrieved {} transactions for customer ID: {}", accountTransactions.size(), id);
            return accountTransactions;

        } catch (BalanceException e) {
            // Re-throw business exceptions
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving transactions for customer ID: {}", id, e);
            throw new InternalServerException(
                    String.format("Unexpected error occurred while retrieving transactions for customer ID: %d", id)
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
