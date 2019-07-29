package com.revised.validation;

import com.revised.model.Account;
import com.revised.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;

public class AccountBalanceConditionTest {

  AccountBalanceCondition condition = new AccountBalanceCondition(1000L);

  @Test
  public void testValidBalance() {
    Account account = TestUtil.getAccountList(1).get(0);
    account.setBalance(2000d);
    Assert.assertTrue(condition.isValid(account, null));
  }

  @Test
  public void testValidEqualBalance() {
    Account account = TestUtil.getAccountList(1).get(0);
    account.setBalance(1000d);
    Assert.assertTrue(condition.isValid(account, null));
  }

  @Test
  public void testInvValidBalance() {
    Account account = TestUtil.getAccountList(1).get(0);
    account.setBalance(999L);
    Assert.assertFalse(condition.isValid(account, null));
  }
}
