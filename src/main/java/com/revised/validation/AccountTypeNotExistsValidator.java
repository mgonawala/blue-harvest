package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Customer;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Function to check if given Account is valid or not. It is used while creation of new Account to
 * check if customer already has an account of same type. It returns true if valid else throws
 * ResourceNotFoundException..
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Component
public class AccountTypeNotExistsValidator implements DualValidator<Account, Customer> {

  @Override
  public boolean isValid(Account account, Customer customer) {
    return !customer.getAccountList().stream()
        .map(Account::getType)
        .collect(Collectors.toSet())
        .contains(account.getType());
  }
}
