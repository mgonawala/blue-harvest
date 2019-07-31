package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import org.junit.Assert;
import org.junit.Test;

public class EnoughAccountBalanceValidatorTest {

  @Test
  public void isValid_CreditTransactionAmountGTAccountBalance_ReturnsTrue() {
    Account account = new Account();
    account.setBalance(101d);
    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.CREDIT);
    transaction.setAmount(100d);

    EnoughAccountBalanceValidator validator = new EnoughAccountBalanceValidator();
    Assert.assertTrue(validator.isValid(account, transaction));
  }

  @Test
  public void isValid_InitialTransactionAmountGTAccountBalance_ReturnsTrue() {
    Account account = new Account();
    account.setBalance(101d);
    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.INITIAL);
    transaction.setAmount(100d);

    EnoughAccountBalanceValidator validator = new EnoughAccountBalanceValidator();
    Assert.assertTrue(validator.isValid(account, transaction));
  }

  @Test
  public void isValid_CreditTransactionAmountEqualsAccountBalance_ReturnsTrue() {
    Account account = new Account();
    account.setBalance(100d);
    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.CREDIT);
    transaction.setAmount(100d);

    EnoughAccountBalanceValidator validator = new EnoughAccountBalanceValidator();
    Assert.assertTrue(validator.isValid(account, transaction));
  }

  @Test
  public void isValid_InitialTransactionAmountEqualsAccountBalance_ReturnsTrue() {
    Account account = new Account();
    account.setBalance(100d);
    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.INITIAL);
    transaction.setAmount(100d);

    EnoughAccountBalanceValidator validator = new EnoughAccountBalanceValidator();
    Assert.assertTrue(validator.isValid(account, transaction));
  }

  @Test
  public void isValid_CreditTransactionAccountBalanceLTTransactionAmount_ReturnsFalse() {
    Account account = new Account();
    account.setBalance(99d);
    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.CREDIT);
    transaction.setAmount(100d);

    EnoughAccountBalanceValidator validator = new EnoughAccountBalanceValidator();
    Assert.assertFalse(validator.isValid(account, transaction));
  }

  @Test
  public void isValid_InitialTransactionAccountBalanceLTTransactionAmount_ReturnsFalse() {
    Account account = new Account();
    account.setBalance(99d);
    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.INITIAL);
    transaction.setAmount(100d);

    EnoughAccountBalanceValidator validator = new EnoughAccountBalanceValidator();
    Assert.assertFalse(validator.isValid(account, transaction));
  }

  @Test
  public void isValid_DebitTransactionAccountBalanceLTTransactionAmount_ReturnsTrue() {
    Account account = new Account();
    account.setBalance(99d);
    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.DEBIT);
    transaction.setAmount(100d);

    EnoughAccountBalanceValidator validator = new EnoughAccountBalanceValidator();
    Assert.assertTrue(validator.isValid(account, transaction));
  }
}
