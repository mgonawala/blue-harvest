package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;

public class EnoughAccountBalanceValidator implements DualValidator<Account, Transaction> {

  @Override
  public boolean isValid(Account object, Transaction transaction) {
    if (transaction.getType().equals(TransactionType.CREDIT)) {
      return object.getBalance() - transaction.getAmount() >= 0;
    }
    return true;
  }

}
