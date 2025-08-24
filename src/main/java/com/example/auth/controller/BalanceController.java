package com.example.auth.controller;

import com.example.auth.model.AccountTransactions;
import com.example.auth.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/myBalance")
    public List<AccountTransactions> retreiveByCustomerId(@RequestParam long id) {
        List<AccountTransactions> accountTransactions = balanceService.
                retreiveByCustomerId(id);
        if (accountTransactions != null) {
            return accountTransactions;
        } else {
            return null;
        }
    }
}


