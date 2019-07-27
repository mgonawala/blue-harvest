package com.revised.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.service.IAccountService;
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
public class AccountControllerTest {

  @MockBean
  IAccountService accountService;
  @Autowired
  private AccountController accountController;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  private String apiGetAllAccount = "/api/v1/accounts";
  private String apiGetAccountById = "/api/v1/accounts/1";
  private String apiGetAllAccountCustomer = "/api/v1/customers/1/accounts";
  private String apiPostNewAccountCustomer = "/api/v1/customers/1/accounts";
  private String apiPostDeleteAccount = "/api/v1/customers/1/accounts/1";
  private List<Account> mockAccounts = new ArrayList<>();

  @Test
  public void testAllAccountsGetOne() throws Exception {
    mockAccounts = TestUtil.getAccountList(1);
    Mockito.when(accountService.findAllAccounts()).thenReturn(mockAccounts);
    mockMvc
        .perform(get(apiGetAllAccount))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  public void testAllAccountsGetFive() throws Exception {
    mockAccounts = TestUtil.getAccountList(5);
    Mockito.when(accountService.findAllAccounts()).thenReturn(mockAccounts);
    mockMvc
        .perform(get(apiGetAllAccount))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  public void testAllAccountsGetEmpty() throws Exception {
    mockAccounts = TestUtil.getAccountList(0);
    Mockito.when(accountService.findAllAccounts()).thenReturn(mockAccounts);
    mockMvc
        .perform(get(apiGetAllAccount))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void getAllAccountOfCustomerValid() throws Exception {

    mockAccounts = TestUtil.getAccountList(1);
    Mockito.when(accountService.findAllAccountsOfCustomer(1L)).thenReturn(mockAccounts);
    mockMvc
        .perform(get(apiGetAllAccountCustomer))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  public void getAllAccountOfCustomerInvalid() throws Exception {

    mockAccounts = TestUtil.getAccountList(1);
    Mockito.when(accountService.findAllAccountsOfCustomer(1L))
        .thenThrow(new ResourceNotFoundException());
    mockMvc.perform(get(apiGetAllAccountCustomer)).andExpect(status().is4xxClientError());
  }

  @Test
  public void createNewAccountValid() throws Exception {
    mockAccounts = TestUtil.getAccountList(1);
    Mockito.when(accountService.createNewAccount(mockAccounts.get(0), 1L))
        .thenReturn(mockAccounts.get(0));
    mockMvc
        .perform(
            post(apiPostNewAccountCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockAccounts.get(0))))
        .andExpect(status().isCreated());
  }

  /*@Test
  public void createNewAccountInValid() throws Exception {
    mockAccounts = TestUtil.getAccountList(1);
    Mockito.when(accountService.createNewAccount(mockAccounts.get(0),1L))
        .thenThrow(new ResourceNotFoundException());
    mockMvc.perform(post(apiPostNewAccountCustomer)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mockAccounts.get(0))))
        .andExpect(status().is4xxClientError());
  }*/

  @Test
  public void getAccountByIdValid() throws Exception {
    mockAccounts = TestUtil.getAccountList(1);
    Mockito.when(accountService.findAccountById(1L)).thenReturn(mockAccounts.get(0));
    mockMvc.perform(get(apiGetAccountById)).andExpect(status().isOk());
  }

  @Test
  public void getAccountByIdInValid() throws Exception {
    mockAccounts = TestUtil.getAccountList(1);
    Mockito.when(accountService.findAccountById(1L)).thenThrow(new ResourceNotFoundException());
    mockMvc.perform(get(apiGetAccountById)).andExpect(status().is4xxClientError());
  }

  @Test
  public void deleterAccountById() throws Exception {
    mockAccounts = TestUtil.getAccountList(1);
    Mockito.doNothing().when(accountService).deleteAccount(1L, 1L);
    mockMvc.perform(delete(apiPostDeleteAccount)).andExpect(status().is2xxSuccessful());
  }

  @Test
  public void deleterAccountByIdInvalid() throws Exception {
    mockAccounts = TestUtil.getAccountList(1);
    Mockito.doThrow(new ResourceNotFoundException()).when(accountService).deleteAccount(1L, 1L);
    mockMvc.perform(delete(apiPostDeleteAccount)).andExpect(status().is4xxClientError());
  }
}
