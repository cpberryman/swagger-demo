package com.berryman.customer.api;

import com.berryman.customer.model.Customer;
import com.berryman.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class Controller {

    private final CustomerService service;

    @Autowired
    public Controller(CustomerService service) {
        this.service = service;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Customer> create(@RequestBody final Customer customer) {
        return new ResponseEntity<Customer>(service.addCustomer(customer), httpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Customer> find(@PathVariable Integer id) {
        return new ResponseEntity<Customer>(service.findCustomerById(id), httpHeaders(), HttpStatus.OK);
    }

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;");
        return headers;
    }

}
