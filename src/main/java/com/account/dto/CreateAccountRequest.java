package com.account.dto;

import com.account.model.AccountType;
import java.math.BigDecimal;

public class CreateAccountRequest {

  private BigDecimal accountBalance;

  private AccountType accountType;

  public BigDecimal getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }
}
