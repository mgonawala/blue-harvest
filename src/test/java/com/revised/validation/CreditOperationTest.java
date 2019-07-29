package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TxStatus;
import com.revised.repository.AccountRepository;
import com.revised.repository.TransactionRepository;
import com.revised.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreditOperationTest {

  @Mock private AccountRepository accountRepository;

  @Mock private TransactionRepository transactionRepository;

  @Test
  public void testValid() {

    CreditOperation creditOperation = new CreditOperation(accountRepository, transactionRepository);
    Transaction transaction = TestUtil.getTransaction(1).get(0);
    Account account = TestUtil.getAccountList(1).get(0);
    account.setBalance(500d);
    transaction.setAmount(200d);

    Transaction mock = TestUtil.getTransaction(1).get(0);
    mock.setStatus(TxStatus.SUCCESS);
    Mockito.when(transactionRepository.save(transaction)).thenReturn(mock);
    Transaction result = creditOperation.apply(transaction, account);
    Assert.assertEquals(TxStatus.SUCCESS, result.getStatus());
  }
}
