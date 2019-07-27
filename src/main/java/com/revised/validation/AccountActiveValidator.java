package com.revised.validation;

import com.revised.model.Account;

public class AccountActiveValidator implements IValidator<Account> {

  @Override
  public boolean isValid(Account object) {
    if (object == null) {
      return false;
    }
    return object.getStatus().equalsIgnoreCase("A");
  }

}
