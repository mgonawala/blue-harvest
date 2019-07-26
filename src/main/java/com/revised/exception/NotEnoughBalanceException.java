package com.revised.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughBalanceException extends RuntimeException {

  public NotEnoughBalanceException() {
    super();
  }

  public NotEnoughBalanceException(String message) {
    super(message);
  }

  public NotEnoughBalanceException(String message, Throwable cause) {
    super(message, cause);
  }

}
