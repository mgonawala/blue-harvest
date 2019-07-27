package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Customer;
import com.revised.repository.CustomerRepository;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerExistValidation implements Function<Long, Customer> {

  CustomerRepository customerRepository;

  @Autowired
  public CustomerExistValidation(CustomerRepository accountRepository) {
    this.customerRepository = accountRepository;
  }

  @Override
  public Customer apply(Long id) {
    Optional<Customer> byId = customerRepository.findById(id);
    if (byId.isPresent()) {
      return byId.get();
    } else {
      throw new ResourceNotFoundException("Customer does not exist:" + id);
    }
  }
}