package com.revised.service;

import com.revised.exception.AccountAlreadyExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import java.util.List;

public interface IAccountService {

  List<Account> findAllAccountsOfCustomer(Long id) throws ResourceNotFoundException;

  Account createNewAccount(Account account, Long customerId) throws AccountAlreadyExistsException;

  Account updateAccountOfCustomer(Account account, Long customerId, Long accountId)
      throws ResourceNotFoundException;

  void deleteAccount(Long accountId, Long customerId) throws ResourceNotFoundException;

  Account findAccountById(Long id) throws ResourceNotFoundException;

  List<Account> findAllAccounts();

}
