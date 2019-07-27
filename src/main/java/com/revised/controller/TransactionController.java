package com.revised.controller;

import com.revised.model.Transaction;
import com.revised.service.ITransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
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

@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(description = "Operations pertaining to Transaction in Banking.")
public class TransactionController {

  @Autowired
  private ITransactionService transactionService;

  @ApiOperation(value = "Operation to find all transaction of given account.")
  @GetMapping("/accounts/{id}/transactions")
  public ResponseEntity<List<Transaction>> getAllTransactionOfAccount(
      @PathVariable("id") @Min(1) Long accountId) {
    return new ResponseEntity<>(transactionService.getAllTransactionOfAccount(accountId),
        HttpStatus.OK);
  }

  @PostMapping("/accounts/{id}/transactions")
  @ApiOperation(value = "Allows to perform transaction on the given account.")
  public ResponseEntity<Transaction> commitTransaction(@RequestBody @Valid Transaction transaction,
      @PathVariable @Min(1) Long id) {
    return new ResponseEntity<>(transactionService.commitTransaction(transaction, id),
        HttpStatus.CREATED);
  }

  @PutMapping("/accounts/{id}/transactions/{tid}")
  @ApiOperation(value = "Allows to revert given transaction.")
  public ResponseEntity<Transaction> revertTransaction(@PathVariable("tid") @Min(1) Long tid,
      @PathVariable("id") @Min(1) Long id) {
    return new ResponseEntity<>(transactionService.revertTransaction(tid, id), HttpStatus.OK);
  }
}
