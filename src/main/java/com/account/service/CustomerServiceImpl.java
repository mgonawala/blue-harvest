package com.account.service;

import com.account.dto.CreateCustomerRequest;
import com.account.dto.CustomerDTO;
import com.account.dto.UpdateCustomerRequest;
import com.account.model.Customer;
import com.account.repository.CustomerRepository;
import com.account.util.ResponseParser;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {

  final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public List<CustomerDTO> findAllCustomers() {
    List<Customer> customers = customerRepository.findAll();
    return ResponseParser.parseCustomers(customers);
  }

  @Override
  @Transactional
  public CustomerDTO findById(Long id) {
    Optional<Customer> byId = customerRepository.findById(id);
    if (byId.isPresent()) {
      return ResponseParser.parseCustomers(byId.get());
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  @Transactional
  public CustomerDTO createNewCustomer(CreateCustomerRequest createCustomerRequest) {
    Customer savedCustomer =
        customerRepository.save(ResponseParser.parseCreateCustomerRequest(createCustomerRequest));
    return ResponseParser.parseCustomers(savedCustomer);
  }

  @Override
  @Transactional
  public CustomerDTO updateCustomerInfo(UpdateCustomerRequest updateCustomerRequest, Long id) {
    Optional<Customer> byId = customerRepository.findById(id);
    if (byId.isPresent()) {
      updateCustomerRequest.getFirstName().ifPresent(v -> byId.get().setFirstName(v));
      updateCustomerRequest.getLastName().ifPresent(v -> byId.get().setLastName(v));
      Customer save = customerRepository.save(byId.get());
      return ResponseParser.parseCustomers(save);
    } else {
      throw new EntityNotFoundException();
    }
  }
}
