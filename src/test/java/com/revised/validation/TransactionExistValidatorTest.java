package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Transaction;
import com.revised.repository.TransactionRepository;
import java.util.Optional;
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
public class TransactionExistValidatorTest {

  @Autowired TransactionExistValidator transactionExistValidator;

  @MockBean private TransactionRepository transactionRepository;

  @Test
  public void apply_ValidTransaction_ReturnsTransaction() {
    Mockito.when(transactionRepository.findById(1L)).thenReturn(Optional.of(new Transaction()));
    Assert.assertNotNull(transactionExistValidator.apply(1L));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void apply_InValidTransaction_ThrowsException() {
    Mockito.when(transactionRepository.findById(1L)).thenReturn(Optional.empty());
    Assert.assertNotNull(transactionExistValidator.apply(1L));
  }
}
