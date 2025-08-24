package com.example.auth.service;

import com.example.auth.model.Loans;

import java.util.List;

public interface LoanService {
    List<Loans> persistByCustomerId(long id);
}
