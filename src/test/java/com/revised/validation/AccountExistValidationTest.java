package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.repository.AccountRepository;
import com.revised.util.TestUtil;
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
public class AccountExistValidationTest {

  @Autowired private AccountExistsValidator validation;

  @MockBean private AccountRepository accountRepository;

  @Test
  public void valid() {
    Account account = TestUtil.getAccountList(1).get(0);
    Optional<Account> mock = Optional.of(account);
    Mockito.when(accountRepository.findById(1L)).thenReturn(mock);
    Assert.assertEquals(account.getId(), validation.apply(1L).getId());
  }

  @Test(expected = ResourceNotFoundException.class)
  public void invalid() {
    Optional<Account> mock = Optional.empty();
    Mockito.when(accountRepository.findById(1L)).thenReturn(mock);
    validation.apply(1L);
  }
}
