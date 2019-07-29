package com.revised.controller;

import com.revised.dto.TransactionDto;
import com.revised.model.Transaction;
import com.revised.service.ITransactionService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that performs operations on Transaction.
 *
 * <p>It provides simple CRUD operations on Transaction Resource. It is capable of producing result
 * in JSON format.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@RestController
@RequestMapping(
    value = "/api/v1",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(description = "Operations pertaining to Transaction in Banking.")
public class TransactionController {

  /** Dependencies * */

  /** Logger * */
  private static final Logger logger = LogManager.getLogger(TransactionController.class);

  /** ModelMapper used to map DTO objects to Entity * */
  @Autowired private ModelMapper modelMapper;

  /** Transaction service carrying business logic * */
  @Autowired private ITransactionService transactionService;

  /**
   * Finds all transactions of given account.
   *
   * @param accountId Acount Id
   * @return Returns list of Transactions' details.
   * @throws org.springframework.web.client.ResourceAccessException if account is not valid.
   */
  @ApiOperation(value = "Operation to find all transaction of given account.")
  @GetMapping("/accounts/{id}/transactions")
  public ResponseEntity<List<Transaction>> getAllTransactionOfAccount(
      @Valid @PathVariable("id") @Min(1) Long accountId) {
    logger.info("Get all transactions of accunt:{}", accountId);
    return new ResponseEntity<>(
        transactionService.getAllTransactionOfAccount(accountId), HttpStatus.OK);
  }

  /**
   * allows to perform a transaction of given account.
   *
   * @param transaction Transaction details.
   * @param id Account Id
   * @return Returns performed transaction details.
   * @throws com.revised.exception.ResourceNotFoundException if account is not valid
   * @throws com.revised.exception.NotEnoughBalanceException if account does not have enough balance
   *     to perform transaction.
   */
  @PostMapping("/accounts/{id}/transactions")
  @ApiOperation(value = "Allows to perform transaction on the given account.")
  public ResponseEntity<Transaction> commitTransaction(
      @RequestBody @Valid TransactionDto transaction, @Valid @PathVariable @Min(1) Long id) {
    logger.info("Perform a new transaction of account:{}", id);
    logger.debug("Transaction request:" + transaction.toString());
    return new ResponseEntity<>(
        transactionService.commitTransaction(modelMapper.map(transaction, Transaction.class), id),
        HttpStatus.CREATED);
  }

  /**
   * Allows to revert a given transaction.
   *
   * @param tid Transaction id to revert
   * @param id Account Id.
   * @return Returns performed transaction details.
   * @throws com.revised.exception.ResourceNotFoundException if account is not valid
   * @throws com.revised.exception.NotEnoughBalanceException if account does not have enough balance
   *     to perform transaction.
   */
  @PutMapping("/accounts/{id}/transactions/{tid}")
  @ApiOperation(value = "Allows to revert given transaction.")
  public ResponseEntity<Transaction> revertTransaction(
      @Valid @PathVariable("tid") @Min(1) Long tid, @Valid @PathVariable("id") @Min(1) Long id) {
    logger.info("Revert transaction:{} for account {}", tid, id);
    return new ResponseEntity<>(transactionService.revertTransaction(tid, id), HttpStatus.OK);
  }
}
