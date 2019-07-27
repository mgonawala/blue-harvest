package com.revised.validation.strategy;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.validation.DualValidator;
import java.util.ArrayList;
import java.util.List;

public class RevertTransactionValidationStrategy implements
    IValidationStrategy<Account, Transaction> {

  List<DualValidator> validatorList = new ArrayList<>();

  @Override
  public void addRule(DualValidator validator) {
    validatorList.add(validator);
  }

  @Override
  public boolean isValid(Account account, Transaction transaction) {
    for (DualValidator val : validatorList) {
      if (!val.isValid(account, transaction)) {
        return false;
      }
    }
    return true;
  }
}
