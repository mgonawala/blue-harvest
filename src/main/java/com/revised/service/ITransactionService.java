package com.revised.service;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Transaction;
import java.util.List;

public interface ITransactionService {

  List<Transaction> getAllTransactionOfAccount(Long id) throws ResourceNotFoundException;

  Transaction commitTransaction(Transaction transaction, Long accountId)
      throws ResourceNotFoundException;

  Transaction revertTransaction(Long transactionId, Long accountId)
      throws ResourceNotFoundException;
}
