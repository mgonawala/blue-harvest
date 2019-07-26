package com.revised.service;

import com.revised.exception.CustomerExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Customer;
import com.revised.repository.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public List<Customer> findAllCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public Customer findCustomerById(Long id) throws ResourceNotFoundException {
    return customerRepository.findById(id).map(customer -> customer).orElseThrow(() ->
        new ResourceNotFoundException("Customer Id is not found:" + id)
    );
  }

  @Override
  public Customer createNewCustomer(Customer customer) throws CustomerExistsException {
    if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
      throw new CustomerExistsException(
          "Customer with the given email already exists:" + customer.getEmail());
    }
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateCustomer(Customer customer, Long id) throws ResourceNotFoundException {
    return customerRepository.findById(id).map(cus -> {
      return customerRepository.save(ICustomerService.parseUpdateRequest(cus, customer));
    }).orElseThrow(() ->
        new ResourceNotFoundException("Customer Id is not found:" + id)
    );
  }

  @Override
  public void deleteCustomer(Long id) throws ResourceNotFoundException {
    customerRepository.findById(id).map(customer -> {
      customerRepository.delete(customer);
      return customer;
    }).orElseThrow(() ->
        new ResourceNotFoundException("Customer Id is not found:" + id)
    );
  }
}
