package com.revised.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Customer;
import com.revised.service.ICustomerService;
import com.revised.util.TestUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

  List<Customer> customerList = new ArrayList<>();
  @Autowired private CustomerController customerController;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private ICustomerService customerService;
  private String deleteCustomer = "/api/v1/customers/1";
  private String updateCustomer = "/api/v1/customers/1";
  private String getCustomer = "/api/v1/customers/1";
  private String getAllCustomers = "/api/v1/customers";

  @Test
  public void testGetAllCustomers() throws Exception {
    customerList = TestUtil.getCustomers(5);
    when(customerService.findAllCustomers()).thenReturn(customerList);
    mockMvc
        .perform(get(getAllCustomers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  public void findCustomerById() throws Exception {
    customerList = TestUtil.getCustomers(1);
    when(customerService.findCustomerById(1L)).thenReturn(customerList.get(0));
    mockMvc.perform(get(getCustomer)).andExpect(status().isOk());
  }

  @Test
  public void testGetAllCustomersEmpty() throws Exception {
    customerList = TestUtil.getCustomers(0);
    when(customerService.findAllCustomers()).thenReturn(customerList);
    mockMvc
        .perform(get(getAllCustomers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void createNewCustomerValid() throws Exception {
    customerList = TestUtil.getCustomers(1);
    when(customerService.createNewCustomer(customerList.get(0))).thenReturn(customerList.get(0));
    mockMvc
        .perform(
            post(getAllCustomers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerList.get(0))))
        .andExpect(status().isCreated());
  }

  @Test
  public void updateCustomerDetail() throws Exception {

    customerList = TestUtil.getCustomers(1);
    when(customerService.updateCustomer(customerList.get(0), 1L)).thenReturn(customerList.get(0));
    mockMvc
        .perform(
            put(getCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerList.get(0))))
        .andExpect(status().isOk());
  }

  @Test
  public void updateCustomerDetailInvalid() throws Exception {

    customerList = TestUtil.getCustomers(1);
    when(customerService.updateCustomer(customerList.get(0), 1L))
        .thenThrow(new ResourceNotFoundException("Resrouce not found"));
    mockMvc
        .perform(
            post(getCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerList.get(0))))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void deleteCustomerInvalid() throws Exception {

    customerList = TestUtil.getCustomers(1);
    Mockito.doThrow(new ResourceNotFoundException("Resource not found"))
        .when(customerService)
        .deleteCustomer(1L);

    mockMvc
        .perform(
            delete(getCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerList.get(0))))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void deleteCustomer() throws Exception {

    customerList = TestUtil.getCustomers(1);
    Mockito.doNothing().when(customerService).deleteCustomer(1L);
    mockMvc
        .perform(
            delete(getCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerList.get(0))))
        .andExpect(status().isNoContent());
  }
}
