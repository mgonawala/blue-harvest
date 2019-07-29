package com.revised.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class Transaction extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private TransactionType type;

  @CreatedDate private Instant transactionDate;

  @NotNull(message = "Please provide valid amount.")
  @DecimalMin("1.00")
  private double amount;

  private TxStatus status;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(nullable = false, updatable = false)
  private Account account;

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

  public Instant getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Instant transactionDate) {
    this.transactionDate = transactionDate;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public TxStatus getStatus() {
    return status;
  }

  public void setStatus(TxStatus status) {
    this.status = status;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
