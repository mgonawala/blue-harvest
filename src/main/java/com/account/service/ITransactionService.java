package com.account.service;

import com.account.dto.CreateTransactionRequest;
import com.account.dto.TransactionDTO;
import com.account.dto.UpdateTransactionRequest;
import java.util.List;

public interface ITransactionService {

  List<TransactionDTO> findAllTransactions();

  TransactionDTO findById(Long id);

  TransactionDTO createNewTransaction(CreateTransactionRequest createTransactionRequest);

  TransactionDTO updateTransaction(UpdateTransactionRequest updateTransactionRequest, Long id);
}
