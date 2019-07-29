package com.revised.controller;

import com.revised.dto.AccountDto;
import com.revised.model.Account;
import com.revised.service.IAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * Controller that performs operations on accounts.
 *
 * <p>It provides simple CRUD operations on Account Resource. It is capable of producing result in
 * JSON format.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@RestController
@RequestMapping(
    value = "/api/v1",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "banking", description = "Operations pertaining to Accounts in Banking.")
public class AccountController {

  /** Dependencies * */

  /** Logger * */
  private static final Logger logger = LogManager.getLogger(AccountController.class);

  /** ModelMapper used to map DTO objects to Entity * */
  @Autowired private ModelMapper modelMapper;

  /** Account service carrying business logic * */
  @Autowired private IAccountService accountService;

  /* ---------------- Public APIs -------------- */

  /**
   * Finds all the accounts of the given Customer.
   *
   * @param id Customer Id
   * @return List of accounts of given customer
   */
  @ApiOperation(value = "View all the Accounts of given customer.")
  @GetMapping("/customers/{id}/accounts")
  public ResponseEntity<List<Account>> getAllAccountsOfCustomer(
      @Valid @PathVariable @Min(value = 1) Long id) {
    logger.info("Get all accounts of customer:{}", id);
    return new ResponseEntity<>(accountService.findAllAccountsOfCustomer(id), HttpStatus.OK);
  }

  /**
   * Creates a new account for given customer.
   *
   * @param account account creation request.
   * @param id customer Id
   * @return returns created account
   * @throws com.revised.exception.ResourceNotFoundException if customer is not valid
   * @throws com.revised.exception.AccountAlreadyExistsException account already exists for the
   *     given customer.
   */
  @ApiOperation(value = "Create a new account for the given customer.")
  @PostMapping("/customers/{id}/accounts")
  public ResponseEntity<Account> createNewAccount(
      @RequestBody @Valid AccountDto account, @PathVariable Long id) {
    logger.info("Create a new account for customer:{}", id);
    logger.debug("Account Request:{}" + account);
    return new ResponseEntity<>(
        accountService.createNewAccount(modelMapper.map(account, Account.class), id),
        HttpStatus.CREATED);
  }

  /**
   * View account details of given account id.
   *
   * @param id Account id
   * @return Returns Account details.
   * @throws com.revised.exception.ResourceNotFoundException if account id invalid.
   */
  @ApiOperation(value = "View account details of given account id.")
  @GetMapping("/accounts/{id}")
  public ResponseEntity<Account> getAccountById(@Valid @PathVariable @Min(value = 1) Long id) {
    logger.info("Get account details of:{}", id);
    return new ResponseEntity<>(accountService.findAccountById(id), HttpStatus.OK);
  }

  /**
   * Returns all the accounts available irrespective of customer.
   *
   * @return Returns List of all accounts available.
   */
  @ApiOperation(value = "View all the Accounts of all customers.")
  @GetMapping("/accounts")
  public ResponseEntity<List<Account>> getAllAccounts() {
    logger.info("Getting all the accounts.");
    return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
  }

  /**
   * Deletes given account of customer.
   *
   * @param customerId Customer Id
   * @param accountId Account Id.
   * @return Returns back Account details which is deleted.
   * @throws com.revised.exception.ResourceNotFoundException if customer/account is not valid.
   */
  @ApiOperation(value = "Operation to delete an account.")
  @DeleteMapping("/customers/{cid}/accounts/{aid}")
  public ResponseEntity<?> deleteAccount(
      @Valid @PathVariable("cid") @Min(1) Long customerId,
      @PathVariable("aid") @Min(1) Long accountId) {
    logger.info("Delete account {} of customer {}", accountId, customerId);
    accountService.deleteAccount(accountId, customerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
