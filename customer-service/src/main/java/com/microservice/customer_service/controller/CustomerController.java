package com.microservice.customer_service.controller;

import com.microservice.customer_service.Entity.Customer;
import com.microservice.customer_service.dto.CustomerDTO;
import com.microservice.customer_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerDTO customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }
}
