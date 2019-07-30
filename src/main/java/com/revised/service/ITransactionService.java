package com.revised.service;

import com.revised.model.Transaction;
import java.util.List;

public interface ITransactionService {

  List<Transaction> getAllTransactionOfAccount(Long id);

  Transaction commitTransaction(Transaction transaction, Long accountId)
     ;

  Transaction revertTransaction(Long transactionId, Long accountId)
     ;
}
