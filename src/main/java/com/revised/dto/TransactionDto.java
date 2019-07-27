package com.revised.dto;

import com.revised.model.TransactionType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TransactionDto {

  private TransactionType type;

  @NotNull(message = "Please provide valid amount.")
  @DecimalMin("1.00")
  private double amount;

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }
}
