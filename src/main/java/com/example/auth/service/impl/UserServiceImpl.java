package com.example.auth.service.impl;

import com.example.auth.model.Customer;
import com.example.auth.repository.CustomerRepository;
import com.example.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> registerUser(Customer customer) {
        try{
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            Customer save = customerRepository.save(customer);
            if(save.getId()>0){
                return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Registration Failed");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
