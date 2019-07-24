package com.account.service;

import com.account.AccountApplication;
import com.account.config.AuditingConfig;
import com.account.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AccountApplication.class, AuditingConfig.class})
public class AccountServiceTest {

  @Autowired
  IAccountService iAccountService;

  @MockBean
  AccountRepository accountRepository;

  @Test
  public void testAccountCreation() {
    // CreateAccountRequest

  }

  public void testAccountCreationFailed() {
  }

  public void testFindAllAccounts() {
  }

  public void testFindAccountById() {
  }

  public void testFindAccountByIdNotFound() {
  }

  public void testUpdateAccountInfo() {
  }

  public void testUpdateAccountInfoFailed() {
  }
}
