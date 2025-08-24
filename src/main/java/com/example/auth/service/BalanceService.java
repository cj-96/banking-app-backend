package com.example.auth.service;

import com.example.auth.model.AccountTransactions;

import java.util.List;

public interface BalanceService {
    List<AccountTransactions> retreiveByCustomerId(long id);
}
