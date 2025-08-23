package com.example.auth.service.impl;

import com.example.auth.exceptionhandling.InvalidCustomerIdException;
import com.example.auth.exceptionhandling.InternalServerException;
import com.example.auth.exceptionhandling.LoanException;
import com.example.auth.model.Loans;
import com.example.auth.repository.LoanRepository;
import com.example.auth.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public List<Loans> persistByCustomerId(long id) {
        log.debug("Attempting to retrieve loans for customer ID: {}", id);

        // Input validation
        validateCustomerId(id);

        try {
            List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);

            if (loans == null || loans.isEmpty()) {
                log.warn("No loans found for customer ID: {}", id);
                throw new LoanException(
                        String.format("No loans found for customer ID: %d", id)
                );
            }

            log.debug("Successfully retrieved {} loans for customer ID: {}", loans.size(), id);
            return loans;

        } catch (LoanException e) {
            // Re-throw business exceptions
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving loans for customer ID: {}", id, e);
            throw new InternalServerException(
                    String.format("Unexpected error occurred while retrieving loans for customer ID: %d", id)
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