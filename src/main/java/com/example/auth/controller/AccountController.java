package com.example.auth.controller;

import com.example.auth.model.Accounts;
import com.example.auth.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountsService;

    @GetMapping("/myAccount")
    public Accounts retriveByCustomerId(@RequestParam long id) {
        Accounts accounts = accountsService.retriveByCustomerId(id);
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }
    }
}


