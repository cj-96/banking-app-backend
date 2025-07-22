package com.example.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    @GetMapping("/loan")
    public String getLoanDetails(){
        return "Here are the loan details from the database";
    }
}


