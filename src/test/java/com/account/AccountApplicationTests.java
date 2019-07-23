package com.account;

import com.account.repository.UserRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserRepositoryTest.class})
public class AccountApplicationTests {

	@Test
	public void contextLoads() {
	}

}
