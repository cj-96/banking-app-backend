package com.example.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @GetMapping("/card")
    public String getMyCardDetails(){
        return "Here are the card details from the database";
    }
}


