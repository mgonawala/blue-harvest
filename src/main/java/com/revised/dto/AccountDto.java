package com.revised.dto;

import com.revised.model.AccountType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class AccountDto {

  @DecimalMin("0")
  private double balance;

  @NotNull
  private AccountType type;


  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

}

