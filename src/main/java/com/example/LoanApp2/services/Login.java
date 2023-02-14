package com.example.LoanApp2.services;

import com.example.LoanApp2.model.Customer;
import com.example.LoanApp2.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Login {
   private final  CustomerRepository repository;

    public void login(Customer customer){


    }
}
