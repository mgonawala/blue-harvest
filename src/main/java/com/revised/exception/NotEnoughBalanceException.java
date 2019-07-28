package com.revised.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotEnoughBalanceException
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughBalanceException extends RuntimeException {

  public NotEnoughBalanceException() {
    super();
  }

  public NotEnoughBalanceException(String message) {
    super(message);
  }
}
