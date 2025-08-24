package com.example.auth.service;

import com.example.auth.model.Cards;

import java.util.List;

public interface CardService {
    List<Cards> retrieveByCustomerId(long id);
}
