package com.berryman.customer.service;

import com.berryman.customer.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    public Customer addCustomer(Customer customer) {
        return customer;
    }

    public Customer findCustomerById(Integer id) {
        //dummy return value
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Banana");
        customer.setSurname("Bananaman");

        return customer;
    }
}
