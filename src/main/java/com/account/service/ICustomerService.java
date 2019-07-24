package com.account.service;

import com.account.dto.CreateCustomerRequest;
import com.account.dto.CustomerDTO;
import com.account.dto.UpdateCustomerRequest;
import java.util.List;

public interface ICustomerService {

  List<CustomerDTO> findAllCustomers();

  CustomerDTO findById(Long id);

  CustomerDTO createNewCustomer(CreateCustomerRequest createCustomerRequest);

  CustomerDTO updateCustomerInfo(UpdateCustomerRequest updateCustomerRequest, Long id);
}
