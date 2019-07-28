package com.revised.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * AccountAlreadyExistsException
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class AccountAlreadyExistsException extends RuntimeException {

  public AccountAlreadyExistsException() {
    super();
  }

  public AccountAlreadyExistsException(String message) {
    super(message);
  }
}
