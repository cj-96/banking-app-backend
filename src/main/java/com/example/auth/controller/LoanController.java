package com.example.auth.controller;

import com.example.auth.model.Loans;
import com.example.auth.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/myLoans")
    public List<Loans> persistByCustomerId(@RequestParam long id) {
        List<Loans> loans = loanService.persistByCustomerId(id);
        if (loans != null) {
            return loans;
        } else {
            return null;
        }
    }
}


