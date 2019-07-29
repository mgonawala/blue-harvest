package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Customer;
import com.revised.repository.CustomerRepository;
import com.revised.util.TestUtil;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerExistValidationTest {

  @Autowired private CustomerExistValidation validation;

  @MockBean private CustomerRepository customerRepository;

  @Test
  public void valid() {
    Customer customer = TestUtil.getCustomers(1).get(0);
    Optional<Customer> mock = Optional.of(customer);
    Mockito.when(customerRepository.findById(1L)).thenReturn(mock);
    Assert.assertEquals(customer.getId(), validation.apply(1L).getId());
  }

  @Test(expected = ResourceNotFoundException.class)
  public void invalid() {
    Optional<Customer> mock = Optional.empty();
    Mockito.when(customerRepository.findById(1L)).thenReturn(mock);
    validation.apply(1L);
  }
}
