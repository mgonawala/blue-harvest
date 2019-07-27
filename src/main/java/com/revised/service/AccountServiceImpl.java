package com.revised.service;

import com.revised.exception.AccountAlreadyExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.model.Customer;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.repository.AccountRepository;
import com.revised.repository.CustomerRepository;
import com.revised.validation.AccountExistsValidator;
import com.revised.validation.CustomerExistValidation;
import com.revised.validation.strategy.IValidationStrategy;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private ITransactionService transactionService;

  @Autowired
  private CustomerExistValidation customerExistValidation;

  @Autowired
  private AccountExistsValidator accountExistsValidator;

  @Autowired
  @Qualifier("createAccount")
  private IValidationStrategy<Account, Customer> createAccountValidationStrategy;

  @Override
  public List<Account> findAllAccountsOfCustomer(Long id) throws ResourceNotFoundException {
    Customer customer = customerExistValidation.apply(id);
    return customer.getAccountList();
  }

  @Override
  public Account createNewAccount(Account account, Long customerId)
      throws AccountAlreadyExistsException {

    Customer cus = customerExistValidation.apply(customerId);
    if (createAccountValidationStrategy.isValid(account, cus)) {
      account.setCustomer(cus);
      account = accountRepository.save(account);
      if (account.getBalance() > 0) {
        Transaction initial = new Transaction();
        initial.setAccount(account);
        initial.setAmount(account.getBalance());
        initial.setType(TransactionType.INITIAL);
        transactionService.commitTransaction(initial, account.getId());
      }
      return account;
    } else {
      throw new ResourceNotFoundException("Can not create account for customer:" + customerId);
    }
  }

  @Override
  public Account updateAccountOfCustomer(Account account, Long customerId, Long accountId) {

    return null;
  }

  @Override
  public void deleteAccount(Long accountId, Long customerId) throws ResourceNotFoundException {
    Customer customer = customerExistValidation.apply(customerId);
    Account account = accountExistsValidator.apply(accountId);
    accountRepository.delete(account);
  }

  @Override
  public Account findAccountById(Long id) throws ResourceNotFoundException {
    return accountExistsValidator.apply(id);
  }

  @Override
  public List<Account> findAllAccounts() {
    return accountRepository.findAll();
  }
}
