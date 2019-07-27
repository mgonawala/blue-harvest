package com.revised.validation;

import com.revised.exception.NotEnoughBalanceException;
import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;

public class TransactionValidCondition implements DualValidator<Account, Transaction> {


  @Override
  public boolean isValid(Account account, Transaction transaction) {
    if(transaction.getType().equals(TransactionType.CREDIT))
      return true;
    else if(transaction.getType().equals(TransactionType.DEBIT))
    {
      if(account.getBalance()>=transaction.getAmount())
        return true;
    }
    else if (transaction.getType().equals(TransactionType.INITIAL))
      return true;
    return false;
  }
}
