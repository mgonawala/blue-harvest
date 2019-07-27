package com.revised.validation;

import com.revised.model.Account;
import com.revised.model.AccountType;
import com.revised.model.Customer;
import com.revised.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;

public class AccountTypeNotExistsValidatorTest {

  @Test
  public void valid(){
    Account account = TestUtil.getAccountList(1).get(0);
    Customer customer = TestUtil.getCustomers(1).get(0);

    customer.getAccountList().add(account);
    customer.getAccountList().get(0).setType(AccountType.CREDIT);

    AccountTypeNotExistsValidator validator = new AccountTypeNotExistsValidator();

    Assert.assertFalse(validator.isValid(account,customer));
  }

  @Test
  public void invalid(){
    Account account = TestUtil.getAccountList(1).get(0);
    Account other = TestUtil.getAccountList(1).get(0);
    other.setType(AccountType.SAVINGS);

    Customer customer = TestUtil.getCustomers(1).get(0);

    customer.getAccountList().add(account);
    customer.getAccountList().get(0).setType(AccountType.CREDIT);

    AccountTypeNotExistsValidator validator = new AccountTypeNotExistsValidator();

    Assert.assertTrue(validator.isValid(other,customer));
  }

}
