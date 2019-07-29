package com.revised.validation.strategy;

import com.revised.model.Account;
import com.revised.validation.DualValidator;

public class TransactionValidactionStrategy implements IValidationStrategy<Account, Account> {

  @Override
  public void addRule(DualValidator validator) {}

  @Override
  public boolean isValid(Account account, Account account2) {
    return false;
  }
}
