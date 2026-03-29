package com.microservice.customer_service.service;

import com.microservice.customer_service.Entity.Customer;
import com.microservice.customer_service.dto.CustomerDTO;
import com.microservice.customer_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public ResponseEntity<Customer> addCustomer(CustomerDTO customer) {
        Customer newCustomer = Customer.builder()
                                       .email(customer.getEmail())
                                       .firstName(customer.getFirstName())
                                       .lastName(customer.getLastName())
                                       .build();
        try {
            return new ResponseEntity<>(customerRepository.save(newCustomer), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerRepository.findAll();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Customer> getCustomerByEmail(String email) {
        try {
            Customer customer = customerRepository.findCustomerByEmailContains(email);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<CustomerDTO> findCustomerById(Long id) {
        try {
            Customer customer = customerRepository.findById(id).orElse(null);

            if (customer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            CustomerDTO response = CustomerDTO.builder()
                                              .customerId(customer.getCustomerId())
                                              .email(customer.getEmail())
                                              .firstName(customer.getFirstName())
                                              .lastName(customer.getLastName())
                                              .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
