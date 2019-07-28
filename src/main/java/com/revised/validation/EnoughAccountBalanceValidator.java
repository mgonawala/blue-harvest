package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Function to check if given Account Balance is enough or not to perfomr given transaction. .
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class EnoughAccountBalanceValidator implements DualValidator<Account, Transaction> {

  /** Dependencies * */

  /**
   * Logger *
   */
  private static final Logger logger = LogManager.getLogger(EnoughAccountBalanceValidator.class);

  /**
   * Account repository for DAO access *
   */
  AccountRepository accountRepository;

  @Override
  public boolean isValid(Account object, Transaction transaction) {
    if (transaction.getType().equals(TransactionType.CREDIT)) {
      return object.getBalance() - transaction.getAmount() >= 0;
    }
    return true;
  }
}
