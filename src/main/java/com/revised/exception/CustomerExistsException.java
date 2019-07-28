package com.revised.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * CustomerExistsException
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerExistsException extends RuntimeException {

  public CustomerExistsException() {
    super();
  }

  public CustomerExistsException(String message) {
    super(message);
  }
}
