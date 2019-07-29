package com.revised.service;

import com.revised.exception.NotEnoughBalanceException;
import com.revised.model.Account;
import com.revised.model.AccountType;
import com.revised.model.Customer;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.model.TxStatus;
import com.revised.repository.AccountRepository;
import com.revised.repository.TransactionRepository;
import com.revised.util.TestUtil;
import com.revised.validation.AccountExistsValidator;
import com.revised.validation.CreditOperation;
import com.revised.validation.DebitOperation;
import com.revised.validation.InitialCreditOperation;
import com.revised.validation.OperationFactory;
import com.revised.validation.TransactionExistValidator;
import com.revised.validation.strategy.IValidationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

  @Autowired TransactionServiceImpl transactionService;

  @MockBean private TransactionRepository transactionRepository;

  @MockBean private AccountRepository accountRepository;

  @MockBean private AccountExistsValidator accountExistsValidator;

  @MockBean private TransactionExistValidator transactionExistValidator;

  @Mock private IValidationStrategy<Account, Transaction> revertTransactionValidation;

  @MockBean private OperationFactory operationFactory;

  @Test
  public void testCreditTransaction() {

    Customer customer = new Customer();
    customer.setAccountList(new ArrayList<>());
    customer.setId(1L);

    Account account = new Account();
    account.setId(1L);
    account.setBalance(100d);
    account.setType(AccountType.CURRENT);
    account.setCustomer(customer);

    Transaction transaction = new Transaction();
    transaction.setAmount(100d);
    transaction.setType(TransactionType.CREDIT);
    transaction.setAccount(account);

    Transaction newTransaction = new Transaction();
    newTransaction.setAccount(account);
    newTransaction.getAccount().setBalance(200d);

    customer.getAccountList().add(account);

    CreditOperation creditOperation = Mockito.mock(CreditOperation.class);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(account);
    Mockito.when(
            operationFactory.getOperation(
                TransactionType.CREDIT, transactionRepository, accountRepository))
        .thenReturn(creditOperation);

    Mockito.when(creditOperation.apply(transaction, account)).thenReturn(newTransaction);

    Transaction result = transactionService.commitTransaction(transaction, account.getId());

    Assert.assertEquals(account.getBalance(), result.getAccount().getBalance(), 0);
  }

  @Test
  public void testDebitTransaction() {

    Customer customer = new Customer();
    customer.setAccountList(new ArrayList<>());
    customer.setId(1L);

    Account account = new Account();
    account.setId(1L);
    account.setBalance(200d);
    account.setType(AccountType.CURRENT);
    account.setCustomer(customer);

    Transaction transaction = new Transaction();
    transaction.setAmount(100d);
    transaction.setType(TransactionType.DEBIT);
    transaction.setAccount(account);

    Transaction newTransaction = new Transaction();
    newTransaction.setAccount(account);
    newTransaction.getAccount().setBalance(100d);

    customer.getAccountList().add(account);

    DebitOperation debitOperation = Mockito.mock(DebitOperation.class);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(account);
    Mockito.when(
            operationFactory.getOperation(
                TransactionType.DEBIT, transactionRepository, accountRepository))
        .thenReturn(debitOperation);

    Mockito.when(debitOperation.apply(transaction, account)).thenReturn(newTransaction);

    Transaction result = transactionService.commitTransaction(transaction, account.getId());

    Assert.assertEquals(account.getBalance(), result.getAccount().getBalance(), 0);
  }

  @Test(expected = NotEnoughBalanceException.class)
  public void testInvalidDebitTransaction() {

    Customer customer = new Customer();
    customer.setAccountList(new ArrayList<>());
    customer.setId(1L);

    Account account = new Account();
    account.setId(1L);
    account.setBalance(50d);
    account.setType(AccountType.CURRENT);
    account.setCustomer(customer);
    Transaction transaction = new Transaction();
    transaction.setAmount(100d);
    transaction.setType(TransactionType.DEBIT);
    transaction.setAccount(account);

    Transaction newTransaction = new Transaction();
    newTransaction.setAccount(account);

    customer.getAccountList().add(account);
    DebitOperation debitOperation = Mockito.mock(DebitOperation.class);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(account);
    Mockito.when(
            operationFactory.getOperation(
                TransactionType.DEBIT, transactionRepository, accountRepository))
        .thenReturn(debitOperation);

    Mockito.when(debitOperation.apply(transaction, account)).thenReturn(newTransaction);

    Transaction result = transactionService.commitTransaction(transaction, account.getId());

    Assert.assertEquals(account.getBalance(), result.getAccount().getBalance(), 0);
  }

  @Test
  public void testInitialCreditTransaction() {

    Customer customer = new Customer();
    customer.setAccountList(new ArrayList<>());
    customer.setId(1L);

    Account account = new Account();
    account.setId(1L);
    account.setBalance(0d);
    account.setType(AccountType.CURRENT);
    account.setCustomer(customer);

    Transaction transaction = new Transaction();
    transaction.setAmount(100d);
    transaction.setType(TransactionType.INITIAL);
    transaction.setAccount(account);

    Transaction newTransaction = new Transaction();
    newTransaction.setAccount(account);
    newTransaction.getAccount().setBalance(100d);

    customer.getAccountList().add(account);

    InitialCreditOperation initialCreditOperation = Mockito.mock(InitialCreditOperation.class);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(account);
    Mockito.when(
            operationFactory.getOperation(
                TransactionType.INITIAL, transactionRepository, accountRepository))
        .thenReturn(initialCreditOperation);

    Mockito.when(initialCreditOperation.apply(transaction, account)).thenReturn(newTransaction);
    Transaction result = transactionService.commitTransaction(transaction, account.getId());
    Assert.assertEquals(account.getBalance(), result.getAccount().getBalance(), 0);
  }

  @Test
  public void revertCreditTransaction() {
    Customer customer = new Customer();
    customer.setAccountList(new ArrayList<>());
    customer.setId(1L);

    Account account = new Account();
    account.setId(1L);
    account.setBalance(200d);
    account.setType(AccountType.CURRENT);
    account.setCustomer(customer);

    Transaction transaction = new Transaction();
    transaction.setId(1L);
    transaction.setAmount(100d);
    transaction.setType(TransactionType.CREDIT);
    transaction.setAccount(account);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(account);
    Mockito.when(transactionExistValidator.apply(1L)).thenReturn(transaction);

    transactionService.setRevertTransactionValidation(revertTransactionValidation);

    Mockito.when(revertTransactionValidation.isValid(account, transaction)).thenReturn(true);

    Transaction newTransaction = new Transaction();
    newTransaction.setId(2L);
    newTransaction.setAccount(account);
    newTransaction.setStatus(TxStatus.REVERT);

    Mockito.when(accountRepository.save(account)).thenReturn(account);
    Mockito.when(transactionRepository.save(transaction)).thenReturn(newTransaction);

    Transaction result = transactionService.revertTransaction(1L, 1L);
    Assert.assertEquals(TxStatus.REVERT, result.getStatus());
    Assert.assertEquals(100d, result.getAccount().getBalance(), 0);
  }

  @Test
  public void revertInitialTransaction() {
    Customer customer = new Customer();
    customer.setAccountList(new ArrayList<>());
    customer.setId(1L);

    Account account = new Account();
    account.setId(1L);
    account.setBalance(200d);
    account.setType(AccountType.CURRENT);
    account.setCustomer(customer);

    Transaction transaction = new Transaction();
    transaction.setId(1L);
    transaction.setAmount(100d);
    transaction.setType(TransactionType.INITIAL);
    transaction.setAccount(account);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(account);
    Mockito.when(transactionExistValidator.apply(1L)).thenReturn(transaction);

    transactionService.setRevertTransactionValidation(revertTransactionValidation);

    Mockito.when(revertTransactionValidation.isValid(account, transaction)).thenReturn(true);

    Transaction newTransaction = new Transaction();
    newTransaction.setId(2L);
    newTransaction.setAccount(account);
    newTransaction.setStatus(TxStatus.REVERT);

    Mockito.when(accountRepository.save(account)).thenReturn(account);
    Mockito.when(transactionRepository.save(transaction)).thenReturn(newTransaction);

    Transaction result = transactionService.revertTransaction(1L, 1L);
    Assert.assertEquals(TxStatus.REVERT, result.getStatus());
    Assert.assertEquals(100d, result.getAccount().getBalance(), 0);
  }

  @Test
  public void revertDebitTransaction() {
    Customer customer = new Customer();
    customer.setAccountList(new ArrayList<>());
    customer.setId(1L);

    Account account = new Account();
    account.setId(1L);
    account.setBalance(200d);
    account.setType(AccountType.CURRENT);
    account.setCustomer(customer);

    Transaction transaction = new Transaction();
    transaction.setId(1L);
    transaction.setAmount(100d);
    transaction.setType(TransactionType.DEBIT);
    transaction.setAccount(account);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(account);
    Mockito.when(transactionExistValidator.apply(1L)).thenReturn(transaction);

    transactionService.setRevertTransactionValidation(revertTransactionValidation);

    Mockito.when(revertTransactionValidation.isValid(account, transaction)).thenReturn(true);

    Transaction newTransaction = new Transaction();
    newTransaction.setId(2L);
    newTransaction.setAccount(account);
    newTransaction.setStatus(TxStatus.REVERT);

    Mockito.when(accountRepository.save(account)).thenReturn(account);
    Mockito.when(transactionRepository.save(transaction)).thenReturn(newTransaction);

    Transaction result = transactionService.revertTransaction(1L, 1L);
    Assert.assertEquals(TxStatus.REVERT, result.getStatus());
    Assert.assertEquals(300d, result.getAccount().getBalance(), 0);
  }

  @Test(expected = NotEnoughBalanceException.class)
  public void revertTransactionException() {
    Customer customer = new Customer();
    customer.setAccountList(new ArrayList<>());
    customer.setId(1L);

    Account account = new Account();
    account.setId(1L);
    account.setBalance(100d);
    account.setType(AccountType.CURRENT);
    account.setCustomer(customer);

    Transaction transaction = new Transaction();
    transaction.setId(1L);
    transaction.setAmount(100d);
    transaction.setType(TransactionType.CREDIT);
    transaction.setAccount(account);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(account);
    Mockito.when(transactionExistValidator.apply(1L)).thenReturn(transaction);

    transactionService.setRevertTransactionValidation(revertTransactionValidation);

    Mockito.when(revertTransactionValidation.isValid(account, transaction)).thenReturn(false);

    Transaction newTransaction = new Transaction();
    newTransaction.setId(2L);
    newTransaction.setAccount(account);
    newTransaction.setStatus(TxStatus.REVERT);

    Mockito.when(accountRepository.save(account)).thenReturn(account);
    Mockito.when(transactionRepository.save(transaction)).thenReturn(newTransaction);

    Transaction result = transactionService.revertTransaction(1L, 1L);
    Assert.assertEquals(TxStatus.REVERT, result.getStatus());
    Assert.assertEquals(300d, result.getAccount().getBalance(), 0);
  }

  @Test
  public void getAllTransactionsOfAccount() {

    List<Transaction> transaction = TestUtil.getTransaction(2);
    Customer customer = TestUtil.getCustomers(1).get(0);

    Mockito.when(accountExistsValidator.apply(1L)).thenReturn(new Account());
    Mockito.when(transactionRepository.findAllByAccount_Id(1L)).thenReturn(transaction);

    List<Transaction> result = transactionService.getAllTransactionOfAccount(1L);
    Assert.assertEquals(2, result.size());
  }
}
