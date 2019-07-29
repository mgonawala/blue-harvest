package com.revised.service;

import com.revised.exception.CustomerExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Customer;
import com.revised.repository.CustomerRepository;
import com.revised.util.TestUtil;
import java.util.ArrayList;
import java.util.List;
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
public class CustomerServiceTest {

  @Autowired ICustomerService customerService;
  List<Customer> customerList = new ArrayList<>();
  @MockBean private CustomerRepository customerRepository;

  @Test
  public void testFindByCustomerId() {
    customerList = TestUtil.getCustomers(1);
    Customer expected = customerList.get(0);

    Mockito.when(customerRepository.findById(1l)).thenReturn(Optional.of(customerList.get(0)));

    Customer result = customerService.findCustomerById(1L);

    Assert.assertEquals(expected.getFirstName(), result.getFirstName());
    Assert.assertEquals(expected.getLastName(), result.getLastName());
    Assert.assertEquals(expected.getEmail(), result.getEmail());
    Assert.assertEquals(expected.getPhoneNumber(), result.getPhoneNumber());
  }

  @Test
  public void createNewCustomer() {
    customerList = TestUtil.getCustomers(1);
    Customer expected = customerList.get(0);

    Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

    Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
        .thenReturn(customerList.get(0));

    Customer result = customerService.createNewCustomer(customerList.get(0));

    Assert.assertEquals(expected.getFirstName(), result.getFirstName());
    Assert.assertEquals(expected.getLastName(), result.getLastName());
    Assert.assertEquals(expected.getEmail(), result.getEmail());
    Assert.assertEquals(expected.getPhoneNumber(), result.getPhoneNumber());
  }

  @Test
  public void updateCustomerDetails() {
    customerList = TestUtil.getCustomers(1);
    Customer expected = customerList.get(0);

    Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customerList.get(0)));

    Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
        .thenReturn(customerList.get(0));

    Customer result = customerService.updateCustomer(customerList.get(0), 1L);

    Assert.assertEquals(expected.getFirstName(), result.getFirstName());
    Assert.assertEquals(expected.getLastName(), result.getLastName());
    Assert.assertEquals(expected.getEmail(), result.getEmail());
    Assert.assertEquals(expected.getPhoneNumber(), result.getPhoneNumber());
  }

  @Test
  public void deleteCustomer() {
    customerList = TestUtil.getCustomers(1);
    Customer expected = customerList.get(0);

    Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customerList.get(0)));

    Mockito.doNothing().when(customerRepository).delete(Mockito.any(Customer.class));
    customerService.deleteCustomer(1L);
    Mockito.verify(customerRepository).delete(Mockito.any(Customer.class));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void exectionWhenInvalidId() {
    customerList = TestUtil.getCustomers(1);
    Customer expected = customerList.get(0);

    Mockito.when(customerRepository.findById(1l)).thenReturn(Optional.empty());
    Customer result = customerService.findCustomerById(1L);
  }

  @Test(expected = CustomerExistsException.class)
  public void exeptionWhenEmailExists() {
    customerList = TestUtil.getCustomers(1);
    Customer expected = customerList.get(0);

    Mockito.when(customerRepository.findByEmail(Mockito.anyString()))
        .thenReturn(Optional.of(customerList.get(0)));
    Customer result = customerService.createNewCustomer(customerList.get(0));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void exceptionWhenUpdateInvalidCustomer() {
    customerList = TestUtil.getCustomers(1);
    Customer expected = customerList.get(0);

    Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
        .thenReturn(customerList.get(0));
    Customer result = customerService.updateCustomer(customerList.get(0), 1L);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void exceptionInDeleteWithInvalidCustomerId() {
    customerList = TestUtil.getCustomers(1);
    Customer expected = customerList.get(0);

    Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());
    customerService.deleteCustomer(1L);
  }

  @Test
  public void findAllCustomer() {
    List<Customer> customers = TestUtil.getCustomers(3);
    Mockito.when(customerRepository.findAll()).thenReturn(customers);
    List<Customer> result = customerService.findAllCustomers();
    Assert.assertEquals(3, result.size());
  }

  @Test
  public void parseUpdateWithNullFirstName() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setFirstName(null);
    response.setFirstName("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("Should Not Change", result.getFirstName());
  }

  @Test
  public void parseUpdateWithEmptyFirstName() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setFirstName("");
    response.setFirstName("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("Should Not Change", result.getFirstName());
  }

  @Test
  public void parseUpdateWithValidFirstName() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setFirstName("This will be updated");
    response.setFirstName("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("This will be updated", result.getFirstName());
  }

  @Test
  public void parseUpdateWithNullLastName() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setLastName(null);
    response.setLastName("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("Should Not Change", result.getLastName());
  }

  @Test
  public void parseUpdateWithEmptyLastName() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setLastName("");
    response.setLastName("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("Should Not Change", result.getLastName());
  }

  @Test
  public void parseUpdateWithValidLastName() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setLastName("This will be updated");
    response.setLastName("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("This will be updated", result.getLastName());
  }

  @Test
  public void parseUpdateWithNullPhoneNumber() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setPhoneNumber(null);
    response.setPhoneNumber("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("Should Not Change", result.getPhoneNumber());
  }

  @Test
  public void parseUpdateWithEmptyPhoneNumber() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setPhoneNumber("");
    response.setPhoneNumber("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("Should Not Change", result.getPhoneNumber());
  }

  @Test
  public void parseUpdateWithValidPhoneNumber() {
    Customer request = TestUtil.getCustomers(1).get(0);
    Customer response = TestUtil.getCustomers(1).get(0);

    request.setPhoneNumber("This will be updated");
    response.setPhoneNumber("Should Not Change");
    Customer result = ICustomerService.parseUpdateRequest(response, request);
    Assert.assertNotNull("This will be updated", result.getPhoneNumber());
  }
}
