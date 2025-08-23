package com.example.auth.service;

import com.example.auth.model.Customer;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> registerUser(Customer customer);
}
