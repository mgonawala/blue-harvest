package com.revised.dto;

import com.revised.model.TransactionType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * Represents DTO class for Transaction resource.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class TransactionDto {

  /**
   * Transaction type CREDIT/DEBIT/INITIAL *
   */
  private TransactionType type;

  /** Transaction amount * */
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

  @Override
  public String toString() {
    return "TransactionDto{" + "type=" + type + ", amount=" + amount + '}';
  }
}
