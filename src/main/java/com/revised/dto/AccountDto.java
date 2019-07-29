package com.revised.dto;

import com.revised.model.AccountType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * Represents DTO class for Account resource.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class AccountDto {

  /** DTO Fields * */

  /** Balance needs to be greater than or equal to0 * */
  @DecimalMin("0")
  private double balance;

  /** Represent account type CURRENT/SAVINGS * */
  @NotNull private AccountType type;

  /** Getter method for account balance * */
  public double getBalance() {
    return balance;
  }

  /** Setter method for account balance * */
  public void setBalance(double balance) {
    this.balance = balance;
  }

  /** Getter for Account type * */
  public AccountType getType() {
    return type;
  }

  /** Setter for Account Type * */
  public void setType(AccountType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "AccountDto{" + "balance=" + balance + ", type=" + type + '}';
  }
}
