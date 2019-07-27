package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.repository.AccountRepository;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountExistsValidator implements Function<Long, Account> {

  AccountRepository accountRepository;

  @Autowired
  public AccountExistsValidator(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account apply(Long id) {
    Optional<Account> byId = accountRepository.findById(id);
    if (byId.isPresent()) {
      return byId.get();
    } else {
      throw new ResourceNotFoundException("Account Number is not Valid:" + id);
    }
  }
}
