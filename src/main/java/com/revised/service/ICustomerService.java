package com.revised.service;

import com.revised.exception.CustomerExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Customer;
import java.util.List;

public interface ICustomerService {

  static Customer parseUpdateRequest(Customer response, Customer request) {
    if (request.getFirstName() != null || !request.getFirstName().isEmpty()) {
      response.setFirstName(request.getFirstName());
    }
    if (request.getLastName() != null || !request.getLastName().isEmpty()) {
      response.setLastName(request.getLastName());
    }
    if (request.getPhoneNumber() != null || !request.getPhoneNumber().isEmpty()) {
      response.setPhoneNumber(request.getPhoneNumber());
    }
    return response;
  }

  List<Customer> findAllCustomers();

  Customer findCustomerById(Long id) throws ResourceNotFoundException;

  Customer createNewCustomer(Customer customer) throws CustomerExistsException;

  Customer updateCustomer(Customer customer, Long id) throws ResourceNotFoundException;

  void deleteCustomer(Long id) throws ResourceNotFoundException;

}
