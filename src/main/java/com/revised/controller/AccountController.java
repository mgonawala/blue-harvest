package com.revised.controller;

import com.revised.dto.AccountDto;
import com.revised.model.Account;
import com.revised.service.IAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.modelmapper.ModelMapper;
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


/**
 *
 * <p> Controller that performs operations on accounts.</p>
 *
 * <p> It provides simple CRUD operations on Account Resource.
 * It is capable of producing result in JSON format.</p>
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "banking", description = "Operations pertaining to Accounts in Banking.")
public class AccountController {

  /** Dependencies **/

  /** ModelMapped used to map DTO objects to Entity **/
  @Autowired
  private ModelMapper modelMapper;

  /** Account service carrying business login **/
  @Autowired
  private IAccountService accountService;

  /* ---------------- Public APIs -------------- */

  /**
   *
   * Finds all the accounts of the given Customer.
   *
   * @param id Customer Id
   * @return List of accounts of given customer
   */
  @ApiOperation(value = "View all the Accounts of given customer.")
  @GetMapping("/customers/{id}/accounts")
  public ResponseEntity<List<Account>> getAllAccountsOfCustomer(@PathVariable @Min(1) Long id) {
    return new ResponseEntity<>(accountService.findAllAccountsOfCustomer(id), HttpStatus.OK);
  }

  @ApiOperation(value = "Create a new account for the given customer.")
  @PostMapping("/customers/{id}/accounts")
  public ResponseEntity<Account> createNewAccount(@RequestBody @Valid AccountDto account,
      @PathVariable Long id) {
    return new ResponseEntity<>(accountService.createNewAccount
        (modelMapper.map(account, Account.class), id), HttpStatus.CREATED);
  }

  @ApiOperation(value = "View account details of given account id.")
  @GetMapping("/accounts/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable @Min(1) Long id) {
    return new ResponseEntity<>(accountService.findAccountById(id), HttpStatus.OK);
  }

  @ApiOperation(value = "View all the Accounts of all customers.")
  @GetMapping("/accounts")
  public ResponseEntity<List<Account>> getAllAccounts() {
    return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
  }

  @ApiOperation(value = "Operation to delete an account.")
  @DeleteMapping("/customers/{cid}/accounts/{aid}")
  public ResponseEntity<?> deleteAccount(@PathVariable("cid") @Min(1) Long customerId,
      @PathVariable("aid") @Min(1) Long accountId) {
    accountService.deleteAccount(accountId, customerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
