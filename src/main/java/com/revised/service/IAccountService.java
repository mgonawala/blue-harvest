package com.revised.service;

import com.revised.model.Account;
import com.revised.validation.strategy.IValidationStrategy;
import java.util.List;

public interface IAccountService {

  List<Account> findAllAccountsOfCustomer(Long id);

  Account createNewAccount(Account account, Long customerId) ;

  void deleteAccount(Long accountId, Long customerId) ;

  void deleteAccount(Long accountId);

  Account findAccountById(Long id);

  List<Account> findAllAccounts();

  IValidationStrategy getCreateAccountValidationStrategy();

  void setCreateAccountValidationStrategy(IValidationStrategy strategy);
}
