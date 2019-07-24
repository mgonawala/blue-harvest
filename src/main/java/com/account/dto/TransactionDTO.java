package com.account.dto;

import com.account.model.Transaction;
import com.account.model.TransactionType;
import java.math.BigDecimal;

public class TransactionDTO {

  private Long id;

  private TransactionType type;

  private BigDecimal amount;

  public TransactionDTO(Transaction transaction) {
    this.amount = transaction.getAmount();
    this.id = transaction.getId();
    this.type = transaction.getType();
  }

  TransactionDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
