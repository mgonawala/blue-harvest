package com.account.controller;

import com.account.dto.AccountDTO;
import com.account.dto.CreateAccountRequest;
import com.account.dto.UpdateAccountRequest;
import com.account.service.IAccountService;
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
public class AccountController {

  final IAccountService accountService;

  public AccountController(IAccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AccountDTO>> getAllAccounts() {
    List<AccountDTO> customers = accountService.findAllAccounts();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping(path = "/accounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
    AccountDTO accounts = accountService.findById(id);
    return new ResponseEntity<>(accounts, HttpStatus.OK);
  }

  @PostMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountDTO> createNewAccount(
      @RequestBody CreateAccountRequest createAccountRequest) {
    AccountDTO newCustomer = accountService.createNewAccount(createAccountRequest);
    return new ResponseEntity<>(newCustomer, HttpStatus.OK);
  }

  @PutMapping(path = "/accounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountDTO> updateAccountInfo(
      @RequestBody UpdateAccountRequest updateAccountRequest, @PathVariable Long id) {
    AccountDTO newAccount = accountService.updateAccountInfo(updateAccountRequest, id);
    return new ResponseEntity<>(newAccount, HttpStatus.OK);
  }
}
