package com.revised.validation;

import com.revised.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * AccountActiveValidator component that validates Account object. It checks whether given account
 * is Active or not. .
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class AccountActiveValidator implements IValidator<Account> {

  /** Dependencies * */

  /**
   * Logger *
   */
  private static final Logger logger = LogManager.getLogger(AccountActiveValidator.class);

  /**
   * Returns true is Status of given account is Active. Otherwise returns false.
   *
   * @param object Account Object
   * @return true/false
   */
  @Override
  public boolean isValid(Account object) {
    if (object == null) {
      logger.debug("AccountActiveValidator: false");
      return false;
    }
    return object.getStatus().equalsIgnoreCase("A");
  }
}
