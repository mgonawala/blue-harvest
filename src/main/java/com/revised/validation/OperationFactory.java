package com.revised.validation;

import com.revised.exception.InvalidOperationException;
import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.repository.AccountRepository;
import com.revised.repository.TransactionRepository;
import java.util.function.BiFunction;
import org.springframework.stereotype.Component;

/**
 * A factory to return related Operation Object based on Transaction Type. .
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Component
public class OperationFactory {

  public BiFunction<Transaction, Account, Transaction> getOperation(
      TransactionType type,
      TransactionRepository transactionRepository,
      AccountRepository accountRepository) {
    switch (type) {
      case CREDIT:
        return new CreditOperation(accountRepository, transactionRepository);
      case INITIAL:
        return new InitialCreditOperation(transactionRepository);
      case DEBIT:
        return new DebitOperation(accountRepository, transactionRepository);
      default:
        throw new InvalidOperationException("Invalid Transaction type:");
    }
  }
}
