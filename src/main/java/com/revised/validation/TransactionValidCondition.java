package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;

/**
 * TransactionValidCondition component that validates Transaction object. .
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class TransactionValidCondition implements DualValidator<Account, Transaction> {

  @Override
  public boolean isValid(Account account, Transaction transaction) {
    if (transaction.getType().equals(TransactionType.CREDIT)) {
      return true;
    } else if (transaction.getType().equals(TransactionType.DEBIT)) {
      return account.getBalance() >= transaction.getAmount();
    } else {
      return transaction.getType().equals(TransactionType.INITIAL);
    }
  }
}
