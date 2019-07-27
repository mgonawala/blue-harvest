package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Customer;

public class AccountBalanceCondition implements DualValidator<Account, Customer> {

  private Long minimumInitialCredit;

  public AccountBalanceCondition(Long balance) {
    this.minimumInitialCredit = balance;
  }


  @Override
  public boolean isValid(Account balance, Customer other) {
    return balance.getBalance() >= minimumInitialCredit;
  }
}
