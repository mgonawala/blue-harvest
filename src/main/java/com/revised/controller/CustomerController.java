package com.revised.controller;

import com.revised.dto.CustomerDto;
import com.revised.model.Customer;
import com.revised.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(description = "Operations pertaining to Customers in Banking.")
public class CustomerController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ICustomerService customerService;

  @GetMapping
  @ApiOperation(value = "Operationtion to find all the customers of Bank.")
  public ResponseEntity<List<Customer>> findAllCustomers() {
    return new ResponseEntity<List<Customer>>(customerService.findAllCustomers(), HttpStatus.OK);
  }

  @ApiOperation(value = "Operation to find a customer by it's id.")
  @GetMapping("/{id}")
  public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {

    return new ResponseEntity<Customer>(customerService.findCustomerById(id), HttpStatus.OK);
  }

  @PostMapping
  @ApiOperation(value = "Operation to create a new customer.")
  public ResponseEntity<Customer> createNewCustomer(@RequestBody CustomerDto customer) {
    return new ResponseEntity<Customer>(customerService.
        createNewCustomer(modelMapper.map(customer, Customer.class)),
        HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Operation to update customer information.")

  public ResponseEntity<Customer> updateCustomer(@RequestBody @Valid CustomerDto customer,
      @PathVariable Long id) {
    return new ResponseEntity<Customer>(customerService.updateCustomer(
        modelMapper.map(customer, Customer.class), id),
        HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "operation to delete a customer.")
  public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
