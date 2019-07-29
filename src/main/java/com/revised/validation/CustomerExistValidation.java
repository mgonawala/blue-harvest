package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Customer;
import com.revised.repository.CustomerRepository;
import java.util.Optional;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Function to check if given customer is valid or not. It returns true if valid else throws
 * ResourceNotFoundException..
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Component
public class CustomerExistValidation implements Function<Long, Customer> {

  /** Dependencies * */

  /** Logger * */
  private static final Logger logger = LogManager.getLogger(CustomerExistValidation.class);

  /** Customer Repository for DAO access * */
  CustomerRepository customerRepository;

  @Autowired
  public CustomerExistValidation(CustomerRepository accountRepository) {
    this.customerRepository = accountRepository;
  }

  /**
   * Returns Customer details is customer with this id exists in DB.
   *
   * @param id Customer Id
   * @return Customer details
   * @throws ResourceNotFoundException
   */
  @Override
  public Customer apply(Long id) {
    Optional<Customer> byId = customerRepository.findById(id);
    if (byId.isPresent()) {
      logger.debug("Customer is valid:{}", id);
      return byId.get();
    } else {
      logger.error("Customer is not valid:{}", id);
      throw new ResourceNotFoundException("Customer does not exist:" + id);
    }
  }
}
