package com.revised.service;

import com.revised.exception.AccountAlreadyExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.model.Customer;
import com.revised.model.Transaction;
import com.revised.repository.AccountRepository;
import com.revised.util.TestUtil;
import com.revised.validation.AccountExistsValidator;
import com.revised.validation.CustomerExistValidation;
import com.revised.validation.strategy.IValidationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

  @Autowired IAccountService accountService;
  List<Account> accountList = new ArrayList<>();
  @MockBean private AccountRepository accountRepository;
  @MockBean private CustomerExistValidation customerExistValidation;
  @MockBean private AccountExistsValidator accountExistsValidator;
  @MockBean private IValidationStrategy<Account, Customer> iValidationStrategy;
  @MockBean private ITransactionService transactionService;

  @Test
  public void findAllAccountOfCustomer_FiveAccount_ReturnsFiveAccount() {
    accountList = TestUtil.getAccountList(5);
    Customer customer = new Customer();
    customer.setAccountList(accountList);
    Mockito.when(customerExistValidation.apply(1L)).thenReturn(customer);

    List<Account> result = accountService.findAllAccountsOfCustomer(1L);
    Assert.assertEquals(5, result.size());
  }

  @Test
  public void createNewAccount_ValidScenario_ReturnsCreatedAccount() {

    Customer customer = TestUtil.getCustomers(1).get(0);
    Account account = TestUtil.getAccountList(1).get(0);
    account.setBalance(0);

    Mockito.when(customerExistValidation.apply(1L)).thenReturn(customer);
    Mockito.when(iValidationStrategy.isValid(account, customer)).thenReturn(true);
    Mockito.when(accountRepository.save(account)).thenReturn(account);
    accountService.setCreateAccountValidationStrategy(iValidationStrategy);
    Account result = accountService.createNewAccount(account, 1L);

    Assert.assertEquals(account.getType(), result.getType());
    Assert.assertEquals(account.getId(), result.getId());
  }

  @Test
  public void createNewAccount_InitialCredit_ReturnsNewAccount() {

    Customer customer = TestUtil.getCustomers(1).get(0);
    Account account = TestUtil.getAccountList(1).get(0);
    account.setBalance(100);

    Mockito.when(customerExistValidation.apply(1L)).thenReturn(customer);
    Mockito.when(iValidationStrategy.isValid(account, customer)).thenReturn(true);
    Mockito.when(accountRepository.save(account)).thenReturn(account);
    accountService.setCreateAccountValidationStrategy(iValidationStrategy);

    Mockito.when(
            transactionService.commitTransaction(Mockito.any(Transaction.class), Mockito.anyLong()))
        .thenReturn(Mockito.any());
    Account result = accountService.createNewAccount(account, 1L);

    Assert.assertEquals(account.getType(), result.getType());
    Assert.assertEquals(account.getId(), result.getId());
  }

  @Test(expected = AccountAlreadyExistsException.class)
  public void createNewAccount_InvalidAccount_ThrowsAccountAlreadyExistsException() {

    Customer customer = TestUtil.getCustomers(1).get(0);
    Account account = TestUtil.getAccountList(1).get(0);
    account.setBalance(100);

    Mockito.when(customerExistValidation.apply(1L)).thenReturn(customer);
    Mockito.when(iValidationStrategy.isValid(account, customer)).thenReturn(false);
    Mockito.when(accountRepository.save(account)).thenReturn(account);
    accountService.setCreateAccountValidationStrategy(iValidationStrategy);

    Mockito.when(
            transactionService.commitTransaction(Mockito.any(Transaction.class), Mockito.anyLong()))
        .thenReturn(Mockito.any());
    Account result = accountService.createNewAccount(account, 1L);

    Assert.assertEquals(account.getType(), result.getType());
  }

  @Test
  public void deleteAccount_ValidScenario_ReturnsNothing() {
    Mockito.when(customerExistValidation.apply(1L)).thenReturn(new Customer());

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(new Account());
    Mockito.doNothing().when(accountRepository).delete(Mockito.any(Account.class));
    accountService.deleteAccount(1L, 1L);
    Mockito.verify(accountRepository).delete(Mockito.any(Account.class));
  }

  @Test
  public void deleteAccountWithOnlyAccountId() {
    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(new Account());
    Mockito.doNothing().when(accountRepository).deleteById(1L);
    accountService.deleteAccount(1L);
    Mockito.verify(accountRepository).deleteById(1L);
  }

  @Test
  public void findAccountById_Valid_ReturnsOneAccount() {
    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(new Account());
    Assert.assertNotNull(accountService.findAccountById(1L));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void findAccountById_Invalid_ReturnsResourceNotFoundException() {
    Mockito.when(accountExistsValidator.apply(1L))
        .thenThrow(new ResourceNotFoundException("Account is not valid."));
    Assert.assertNotNull(accountService.findAccountById(1L));
  }

  @Test
  public void findAll_FiveAccounts_RetrunsFiveAccounts() {
    List<Account> accountList = TestUtil.getAccountList(5);
    Mockito.when(accountRepository.findAll()).thenReturn(accountList);
    Assert.assertEquals(5, accountList.size());
  }

  @Test(expected = ResourceNotFoundException.class)
  public void deleteAccount_CustomerInvalid_ThrowsResourceNotFoundException() {
    Mockito.when(customerExistValidation.apply(1L))
        .thenThrow(new ResourceNotFoundException("Customer is not valid."));

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(new Account());

    accountService.deleteAccount(1L, 1L);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void deleteAccount_AccountInvalid_ThrowsResoruceNotFoundException() {
    Mockito.when(accountExistsValidator.apply(1L))
        .thenThrow(new ResourceNotFoundException("Account is not valid."));

    Mockito.when(customerExistValidation.apply(1L)).thenReturn(new Customer());

    accountService.deleteAccount(1L, 1L);
  }
}
