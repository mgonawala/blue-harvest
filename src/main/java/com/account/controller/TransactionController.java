package com.account.controller;

import com.account.dto.CreateTransactionRequest;
import com.account.dto.TransactionDTO;
import com.account.dto.UpdateTransactionRequest;
import com.account.service.ITransactionService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  final ITransactionService transactionService;

  public TransactionController(ITransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping(path = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TransactionDTO>> getAllCustomers() {
    List<TransactionDTO> customers = transactionService.findAllTransactions();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping(path = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionDTO> getAllCustomers(@PathVariable Long id) {
    TransactionDTO customers = transactionService.findById(id);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @PostMapping(path = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionDTO> createNewCustomer(
      @RequestBody CreateTransactionRequest createTransactionRequest) {
    TransactionDTO newCustomer = transactionService.createNewTransaction(createTransactionRequest);
    return new ResponseEntity<>(newCustomer, HttpStatus.OK);
  }

  @PutMapping(path = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionDTO> updateCustomerInfo(
      @RequestBody UpdateTransactionRequest updateTransactionRequest, @PathVariable Long id) {
    TransactionDTO newCustomer = transactionService.updateTransaction(updateTransactionRequest, id);
    return new ResponseEntity<>(newCustomer, HttpStatus.OK);
  }
}
