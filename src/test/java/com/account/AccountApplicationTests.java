package com.account;

import com.account.model.UserModelTest;
import com.account.repository.UserRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserRepositoryTest.class,
    UserModelTest.class})
public class AccountApplicationTests {

  @Test
  public void contextLoads() {}
}
