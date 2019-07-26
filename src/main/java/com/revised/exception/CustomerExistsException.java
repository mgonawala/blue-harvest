package com.revised.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerExistsException extends RuntimeException {

  public CustomerExistsException() {
    super();
  }

  public CustomerExistsException(String message) {
    super(message);
  }

  public CustomerExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
