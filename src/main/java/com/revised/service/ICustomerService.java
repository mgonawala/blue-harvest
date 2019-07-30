package com.revised.service;


import com.revised.model.Customer;
import java.util.List;

public interface ICustomerService {

  String CUSTOMER_ID_IS_NOT_FOUND = "Customer Id is not found:";

  static Customer parseUpdateRequest(Customer response, Customer request) {
    if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
      response.setFirstName(request.getFirstName());
    }
    if (request.getLastName() != null && !request.getLastName().isEmpty()) {
      response.setLastName(request.getLastName());
    }
    if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
      response.setPhoneNumber(request.getPhoneNumber());
    }
    return response;
  }

  List<Customer> findAllCustomers();

  Customer findCustomerById(Long id);

  Customer createNewCustomer(Customer customer);

  Customer updateCustomer(Customer customer, Long id);

  void deleteCustomer(Long id);
}
