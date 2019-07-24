package com.account.dto;

import com.account.model.AccountType;
import java.math.BigDecimal;
import java.util.Optional;

public class UpdateAccountRequest {

  private Optional<BigDecimal> accountBalance = Optional.empty();

  private Optional<AccountType> accountType = Optional.empty();

  public Optional<BigDecimal> getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(Optional<BigDecimal> accountBalance) {
    this.accountBalance = accountBalance;
  }

  public Optional<AccountType> getAccountType() {
    return accountType;
  }

  public void setAccountType(Optional<AccountType> accountType) {
    this.accountType = accountType;
  }
}
