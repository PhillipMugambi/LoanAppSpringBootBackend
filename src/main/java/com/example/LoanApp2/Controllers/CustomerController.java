package com.example.LoanApp2.Controllers;
import com.example.LoanApp2.model.Customer;
import com.example.LoanApp2.services.Registeration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    Registeration registeration;
    @PostMapping("registercustomer")
    public void save(@RequestBody Customer customer) {
        registeration.registercustomer(customer);
    }}

