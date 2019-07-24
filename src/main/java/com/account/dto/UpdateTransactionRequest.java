package com.account.dto;

import com.account.model.TransactionType;
import java.math.BigDecimal;
import java.util.Optional;

public class UpdateTransactionRequest {

  private Optional<TransactionType> type = Optional.empty();

  private Optional<BigDecimal> amount = Optional.empty();

  public Optional<TransactionType> getType() {
    return type;
  }

  public void setType(Optional<TransactionType> type) {
    this.type = type;
  }

  public Optional<BigDecimal> getAmount() {
    return amount;
  }

  public void setAmount(Optional<BigDecimal> amount) {
    this.amount = amount;
  }
}
