package com.revised.controller;

import com.revised.exception.AccountAlreadyExistsException;
import com.revised.exception.CustomerExistsException;
import com.revised.exception.InvalidOperationException;
import com.revised.exception.NotEnoughBalanceException;
import com.revised.exception.ResourceNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** Global exception handler advice to catch all Application exceptions. */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Catches validation exception thrown by Spring validator framework.
   *
   * @param ex ValidationException
   * @param headers
   * @param status
   * @param request
   * @return
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", new Date());
    body.put("status", status.value());
    // Get all errors
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(x -> x.getField() + " " + x.getDefaultMessage())
            .collect(Collectors.toList());

    body.put("errors", errors);
    logger.info("Error in validation.");
    logger.debug("Validation errors:" + errors);
    return new ResponseEntity<>(body, headers, status);
  }

  /**
   * Catches ConstraintValidation exception.
   *
   * @param response returns response with error BAD_REQUEST
   * @throws IOException
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> constraintViolationException(
      ConstraintViolationException ex, HttpServletResponse response) throws IOException {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", new Date());
    body.put("status", HttpStatus.NOT_FOUND.value());
    body.put("errors", Arrays.asList(ex.getMessage()));
    logger.info("Error in validation.");
    logger.debug("Validation errors:" + ex.getMessage());
    return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<Object> handleUserNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", new Date());
    body.put("status", HttpStatus.NOT_FOUND.value());
    body.put("errors", Arrays.asList(ex.getMessage()));
    logger.info("Error in validation.");
    logger.debug("Validation errors:" + ex.getMessage());
    return new ResponseEntity(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    CustomerExistsException.class,
    AccountAlreadyExistsException.class,
    NotEnoughBalanceException.class,
    InvalidOperationException.class
  })
  public final ResponseEntity<Object> customerNotFound(Exception ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", new Date());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("errors", Arrays.asList(ex.getMessage()));
    logger.info("Error in validation.");
    logger.debug("Validation errors:" + ex.getMessage());
    return new ResponseEntity(body, HttpStatus.NOT_FOUND);
  }
}
