package com.revised.controller;

import com.revised.model.Account;
import com.revised.service.IAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "banking", description = "Operations pertaining to Accounts in Banking.")
public class AccountController {

  @Autowired
  private IAccountService accountService;

  @ApiOperation(value = "View all the Accounts of given customer.")
  @GetMapping("/customers/{id}/accounts")
  public ResponseEntity<List<Account>> getAllAccountsOfCustomer(@PathVariable Long id) {
    return new ResponseEntity<>(accountService.findAllAccountsOfCustomer(id), HttpStatus.OK);
  }

  @ApiOperation(value = "Create a new account for the given customer.")
  @PostMapping("/customers/{id}/accounts")
  public ResponseEntity<Account> createNewAccount(@RequestBody Account account,
      @PathVariable Long id) {
    return new ResponseEntity<>(accountService.createNewAccount(account, id), HttpStatus.CREATED);
  }

  @ApiOperation(value = "View account details of given account id.")
  @GetMapping("/accounts/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
    return new ResponseEntity<>(accountService.findAccountById(id), HttpStatus.OK);
  }

  @ApiOperation(value = "View all the Accounts of all customers.")
  @GetMapping("/accounts")
  public ResponseEntity<List<Account>> getAllAccounts() {
    return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
  }

  @ApiOperation(value = "Operation to delete an account.")
  @DeleteMapping("/customers/{cid}/accounts/{aid}")
  public ResponseEntity<?> deleteAccount(@PathVariable("cid") Long customerId,
      @PathVariable("aid") Long accountId) {
    accountService.deleteAccount(accountId, customerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
