package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TxStatus;
import com.revised.repository.AccountRepository;
import com.revised.repository.TransactionRepository;
import java.util.function.BiFunction;

/**
 * Function to perform Credit transaction on given Account..
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class CreditOperation implements BiFunction<Transaction, Account, Transaction> {

  /** Account repository for DAO Access * */
  private AccountRepository accountRepository;

  /** Transaction Repository for DAO Access * */
  private TransactionRepository transactionRepository;

  public CreditOperation(
      AccountRepository accountRepository, TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction apply(Transaction transaction, Account account) {
    transaction.setAccount(account);
    double balance = account.getBalance();
    balance = balance + transaction.getAmount();
    account.setBalance(balance);
    accountRepository.save(account);
    transaction.setStatus(TxStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }
}
