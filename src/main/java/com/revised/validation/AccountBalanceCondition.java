package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Customer;

/**
 * AccountBalanceCondition component that validates Account object. It is used while creating a new
 * Account. It allows to configure & validate minimum initial credit for Account creation. .
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class AccountBalanceCondition implements DualValidator<Account, Customer> {

  private Long minimumInitialCredit;

  public AccountBalanceCondition(Long balance) {
    this.minimumInitialCredit = balance;
  }

  /** Returns true if account balane is greter than or equals to minimum valid credit. */
  @Override
  public boolean isValid(Account balance, Customer other) {
    return balance.getBalance() >= minimumInitialCredit;
  }
}
