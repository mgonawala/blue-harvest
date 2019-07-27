package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.repository.TransactionRepository;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;

public class InitialCreditOperation implements BiFunction<Transaction, Account, Transaction> {

  private TransactionRepository transactionRepository;

  @Autowired
  public InitialCreditOperation(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction apply(Transaction transaction, Account account) {
    return transactionRepository.save(transaction);
  }
}
