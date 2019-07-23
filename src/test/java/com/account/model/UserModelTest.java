package com.account.model;

import org.junit.Assert;
import org.junit.Test;

public class UserModelTest {

  private User user = new User();

  @Test
  public void testFirstName() {
    user.setFirstName("Test");
    Assert.assertEquals("Test", user.getFirstName());
  }

  @Test
  public void testLastName() {
    user.setLastName("Model");
    Assert.assertEquals("Model", user.getLastName());
  }

  @Test
  public void testCustomerID() {
    user.setCustomerID(52456890L);
    Assert.assertEquals(new Long(52456890), user.getCustomerID());
  }
}
