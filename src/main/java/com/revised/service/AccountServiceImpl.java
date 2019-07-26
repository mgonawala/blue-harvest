package com.revised.service;

import com.revised.exception.AccountAlreadyExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.repository.AccountRepository;
import com.revised.repository.CustomerRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Override
  public List<Account> findAllAccountsOfCustomer(Long id) throws ResourceNotFoundException {
    if (customerRepository.findById(id).isPresent()) {
      return accountRepository.findByCustomer_Id(id);
    }
    throw new ResourceNotFoundException("Customer id does not exits:" + id);
  }

  @Override
  public Account createNewAccount(Account account, Long customerId)
      throws AccountAlreadyExistsException {

    Account account1 = customerRepository
        .findById(customerId)
        .map(
            customer -> {
              if (accountRepository.findByCustomer_IdAndType(customerId, account.getType())
                  .isPresent()) {
                throw new AccountAlreadyExistsException(
                    "Account exists for type " + account.getType()
                        + " and customer id:" + customerId);
              }

              account.setCustomer(customer);
              return account;
            }
        )
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found:" + customerId));
    account1 = accountRepository.save(account1);
    if (account1.getBalance() > 0) {
      Transaction initial = new Transaction();
      initial.setAccount(account1);
      initial.setAmount(account1.getBalance());
      initial.setType(TransactionType.INITIAL);
      transactionService.commitTransaction(initial, account1.getId());
    }
    return account1;
  }

  @Override
  public Account updateAccountOfCustomer(Account account, Long customerId, Long accountId) {
    /* customerRepository.findById(customerId)
         .map(customer -> {
           accountRepository.findById(accountId)
               .map(account1 -> {

               })
               .orElseThrow(new ResourceNotFoundException("Account doesnt exist:"+accountId));
         })
         .orElseThrow( new ResourceNotFoundException("Customer does not exist:"+customerId));*/
    return null;
  }

  @Override
  public void deleteAccount(Long accountId, Long customerId) throws ResourceNotFoundException {
    if (customerRepository.findById(customerId).isPresent()) {
      accountRepository.deleteById(accountId);
    } else {
      throw new ResourceNotFoundException("Customer id does not exist:" + customerId);
    }
  }

  @Override
  public Account findAccountById(Long id) throws ResourceNotFoundException {
    return accountRepository.findById(id)
        .map(account -> account)
        .orElseThrow(() -> new ResourceNotFoundException("Account doesnt exist:" + id));

  }

  @Override
  public List<Account> findAllAccounts() {
    return accountRepository.findAll();
  }
}
