package com.revised.validation;


import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.repository.AccountRepository;
import com.revised.repository.TransactionRepository;
import java.util.function.BiFunction;

public class DebitOperation implements BiFunction<Transaction, Account, Transaction> {

  private AccountRepository accountRepository;
  private TransactionRepository transactionRepository;

  public DebitOperation(AccountRepository accountRepository,
      TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction apply(Transaction transaction, Account account) {
    transaction.setAccount(account);
    double balance = account.getBalance();
    balance = balance - transaction.getAmount();
    account.setBalance(balance);
    accountRepository.save(account);
    return transactionRepository.save(transaction);
  }
}
