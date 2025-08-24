package com.example.auth.controller;

import com.example.auth.model.Cards;
import com.example.auth.repository.CardsRepository;
import com.example.auth.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsController {

    private final CardService cardService;

    @GetMapping("/myCards")
    public List<Cards> retrieveByCustomerId(@RequestParam long id) {
        List<Cards> cards = cardService.retrieveByCustomerId(id);
        if (cards != null ) {
            return cards;
        }else {
            return null;
        }
    }
}


