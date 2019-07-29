package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.repository.AccountRepository;
import com.revised.repository.TransactionRepository;
import java.util.function.BiFunction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationFactoryTest {

  @Autowired private OperationFactory operationFactory;

  @MockBean private TransactionRepository transactionRepository;

  @MockBean private AccountRepository accountRepository;

  @Test
  public void getCreditOperation() {
    BiFunction<Transaction, Account, Transaction> operation =
        operationFactory.getOperation(
            TransactionType.CREDIT, transactionRepository, accountRepository);
    Assert.assertEquals(CreditOperation.class, operation.getClass());
  }

  @Test
  public void getDebitOperation() {
    BiFunction<Transaction, Account, Transaction> operation =
        operationFactory.getOperation(
            TransactionType.DEBIT, transactionRepository, accountRepository);
    Assert.assertEquals(DebitOperation.class, operation.getClass());
  }

  @Test
  public void getInitialOperation() {
    BiFunction<Transaction, Account, Transaction> operation =
        operationFactory.getOperation(
            TransactionType.INITIAL, transactionRepository, accountRepository);
    Assert.assertEquals(InitialCreditOperation.class, operation.getClass());
  }
}
