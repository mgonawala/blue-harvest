package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TxStatus;
import com.revised.repository.TransactionRepository;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Function to perform Initial Credit transaction on given Account..
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class InitialCreditOperation implements BiFunction<Transaction, Account, Transaction> {

  /** Transaction repository for DAO access * */
  private TransactionRepository transactionRepository;

  @Autowired
  public InitialCreditOperation(TransactionRepository transactionRepository) {

    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction apply(Transaction transaction, Account account) {
    transaction.setStatus(TxStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }
}
