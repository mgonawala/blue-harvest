package com.revised.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revised.model.Transaction;
import com.revised.service.ITransactionService;
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
public class TransactionControllerTest {

  List<Transaction> transactionList = new ArrayList<>();
  @Autowired private TransactionController transactionController;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private ITransactionService transactionService;
  private String revertTransaction = "/api/v1/accounts/1/transactions/1";
  private String newTransaction = "/api/v1/accounts/1/transactions";
  private String getAllTransactionOfAccount = "/api/v1/accounts/1/transactions";

  @Test
  public void getAllTransactionOfAccount() throws Exception {

    transactionList = TestUtil.getTransaction(5);
    when(transactionService.getAllTransactionOfAccount(1L)).thenReturn(transactionList);
    mockMvc
        .perform(get(getAllTransactionOfAccount))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  public void getAllTransactionOfAccountEMpty() throws Exception {

    transactionList = TestUtil.getTransaction(0);
    when(transactionService.getAllTransactionOfAccount(1L)).thenReturn(transactionList);
    mockMvc
        .perform(get(getAllTransactionOfAccount))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void createNewTransaction() throws Exception {
    transactionList = TestUtil.getTransaction(1);
    when(transactionService.commitTransaction(transactionList.get(0), 1l))
        .thenReturn(transactionList.get(0));
    mockMvc
        .perform(
            post(newTransaction)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionList.get(0))))
        .andExpect(status().isCreated());
  }

  @Test
  public void revertTransaction() throws Exception {

    transactionList = TestUtil.getTransaction(1);
    Mockito.doReturn(transactionList.get(0)).when(transactionService).revertTransaction(1L, 1L);
    mockMvc
        .perform(
            put(revertTransaction)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionList.get(0))))
        .andExpect(status().isOk());
  }
}
