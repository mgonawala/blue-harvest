package com.account.repository;

import com.account.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

  @Autowired UserRepository userRepository;

  @Test
  public void saveAndFindOneUser() {
    User user = new User("Test", "Local");
    User save = userRepository.save(user);
    Assert.assertEquals("Test", save.getFirstName());
    Assert.assertEquals("Local", save.getLastName());
    Assert.assertNotNull(save.getCustomerID());
  }
}
