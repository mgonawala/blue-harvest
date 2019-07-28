package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.repository.AccountRepository;
import java.util.Optional;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Function to check if given Account is valid or not. It returns true if valid else throws
 * ResourceNotFoundException..
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Component
public class AccountExistsValidator implements Function<Long, Account> {

  /** Dependencies * */

  /**
   * Logger *
   */
  private static final Logger logger = LogManager.getLogger(AccountExistsValidator.class);

  /**
   * Account repository for DAO access *
   */
  AccountRepository accountRepository;

  @Autowired
  public AccountExistsValidator(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  /**
   * Returns Account details if given account id is Valid.
   *
   * @param id
   * @return Account details
   * @throws ResourceNotFoundException
   */
  @Override
  public Account apply(Long id) {
    Optional<Account> byId = accountRepository.findById(id);
    if (byId.isPresent()) {
      logger.debug("AccountExistsValidator:true");
      return byId.get();
    } else {
      logger.error("Account id is not valid:{}",id);
      throw new ResourceNotFoundException("Account Number is not Valid:" + id);
    }
  }
}
