package com.revised.validation;

import com.revised.model.Transaction;
import com.revised.model.TxStatus;
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
public class InitialCreditOperationTest {

  @Mock private TransactionRepository transactionRepository;

  @Test
  public void apply_InitialCredit_RetrurnsSuccessStatus() {

    InitialCreditOperation initialCreditOperation =
        new InitialCreditOperation(transactionRepository);
    Transaction transaction = TestUtil.getTransaction(1).get(0);
    transaction.setAmount(200d);

    Transaction mock = TestUtil.getTransaction(1).get(0);
    transaction.setAmount(200d);
    mock.setStatus(TxStatus.SUCCESS);
    Mockito.when(transactionRepository.save(transaction)).thenReturn(mock);
    Transaction result = initialCreditOperation.apply(transaction, null);
    Assert.assertEquals(TxStatus.SUCCESS, result.getStatus());
  }
}
