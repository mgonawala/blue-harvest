package com.revised.service;

import com.revised.exception.CustomerExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Customer;
import com.revised.repository.CustomerRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CustomerService carries business logic for operations allowed on Customer resource.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

  /** Dependencies * */

  /** Logger * */
  private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

  /** Customer repository for DAO access * */
  @Autowired private CustomerRepository customerRepository;

  /**
   * Find all customers.
   *
   * @return List of customer details
   */
  @Override
  public List<Customer> findAllCustomers() {
    return customerRepository.findAll();
  }

  /**
   * Find customer details of given customer.
   *
   * @param id Customer id
   * @return Customer details
   * @throws ResourceNotFoundException
   */
  @Override
  public Customer findCustomerById(Long id) throws ResourceNotFoundException {
    Customer result =
        customerRepository
            .findById(id)
            .map(customer -> customer)
            .orElseThrow(() -> new ResourceNotFoundException("Customer Id is not found:" + id));
    logger.debug("Customer result:{}", result);
    return result;
  }

  /**
   * Allows to register new operation. Doesn't allow to register with same email id.
   *
   * @param customer new Customer details
   * @return Returns newly created customer
   * @throws CustomerExistsException
   */
  @Override
  public Customer createNewCustomer(Customer customer) throws CustomerExistsException {
    if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
      throw new CustomerExistsException(
          "Customer with the given email already exists:" + customer.getEmail());
    }
    Customer result = customerRepository.save(customer);
    logger.debug("Customer Result:{}", result);
    logger.info("Customer created successfully:{}", result.getId());
    return result;
  }

  /**
   * Operation to update customer details.
   *
   * @param customer Customer details to update
   * @param id Customer id
   * @return Updated customer details
   * @throws ResourceNotFoundException
   */
  @Override
  public Customer updateCustomer(Customer customer, Long id) throws ResourceNotFoundException {
    Customer result =
        customerRepository
            .findById(id)
            .map(
                cus -> {
                  return customerRepository.save(
                      ICustomerService.parseUpdateRequest(cus, customer));
                })
            .orElseThrow(() -> new ResourceNotFoundException("Customer Id is not found:" + id));
    logger.debug("Customer Result:{}", result);
    logger.info("Customer created successfully:{}", result.getId());
    return result;
  }

  /**
   * Allows to delete a customer from system.
   *
   * @param id Customer id
   * @throws ResourceNotFoundException
   */
  @Override
  public void deleteCustomer(Long id) throws ResourceNotFoundException {
    customerRepository
        .findById(id)
        .map(
            customer -> {
              customerRepository.delete(customer);
              return customer;
            })
        .orElseThrow(() -> new ResourceNotFoundException("Customer Id is not found:" + id));
    logger.info("Customer deleyed successfully:{}", id);
  }
}
