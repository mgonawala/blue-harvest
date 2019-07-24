package com.account.controller;

import com.account.dto.CreateCustomerRequest;
import com.account.dto.CustomerDTO;
import com.account.dto.UpdateCustomerRequest;
import com.account.service.ICustomerService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  final ICustomerService customerService;

  public CustomerController(ICustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
    List<CustomerDTO> customers = customerService.findAllCustomers();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerDTO> getAllCustomers(@PathVariable Long id) {
    CustomerDTO customers = customerService.findById(id);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @PostMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerDTO> createNewCustomer(
      @RequestBody CreateCustomerRequest createCustomerRequest) {
    CustomerDTO newCustomer = customerService.createNewCustomer(createCustomerRequest);
    return new ResponseEntity<>(newCustomer, HttpStatus.OK);
  }

  @PutMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerDTO> updateCustomerInfo(
      @RequestBody UpdateCustomerRequest updateCustomerRequest, @PathVariable Long id) {
    CustomerDTO newCustomer = customerService.updateCustomerInfo(updateCustomerRequest, id);
    return new ResponseEntity<>(newCustomer, HttpStatus.OK);
  }
}
