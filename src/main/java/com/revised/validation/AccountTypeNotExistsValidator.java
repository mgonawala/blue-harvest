package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Customer;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AccountTypeNotExistsValidator implements DualValidator<Account, Customer> {

  @Override
  public boolean isValid(Account account, Customer customer) {
    return !customer.getAccountList().stream()
        .map(a -> a.getType())
        .collect(Collectors.toSet())
        .contains(account.getType());
  }
}