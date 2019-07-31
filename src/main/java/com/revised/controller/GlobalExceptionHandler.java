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
import org.springframework.dao.DataIntegrityViolationException;
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

  public static final String TIMESTAMP = "timestamp";
  public static final String STATUS = "status";
  public static final String ERRORS = "errors";
  public static final String ERROR_IN_VALIDATION = "Error in validation.";
  public static final String VALIDATION_ERRORS = "Validation errors:";

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
    body.put(TIMESTAMP, new Date());
    body.put(STATUS, status.value());
    // Get all errors
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(x -> x.getField() + " " + x.getDefaultMessage())
            .collect(Collectors.toList());

    body.put(ERRORS, errors);
    logger.info(ERROR_IN_VALIDATION);
    logger.debug(VALIDATION_ERRORS + errors);
    return new ResponseEntity<>(body, headers, status);
  }

  /**
   * Catches ConstraintValidation exception.
   *
   * @param response returns response with error BAD_REQUEST
   * @throws IOException
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> constraintViolationException(
      DataIntegrityViolationException ex, HttpServletResponse response) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, new Date());
    body.put(STATUS, HttpStatus.CONFLICT.value());
    body.put(ERRORS, Arrays.asList(ex.getMessage()));
    logger.info(ERROR_IN_VALIDATION);
    logger.debug(VALIDATION_ERRORS + ex.getMessage());
    return new ResponseEntity(body, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<Object> handleUserNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, new Date());
    body.put(STATUS, HttpStatus.NOT_FOUND.value());
    body.put(ERRORS, Arrays.asList(ex.getMessage()));
    logger.info(ERROR_IN_VALIDATION);
    logger.debug(VALIDATION_ERRORS + ex.getMessage());
    return new ResponseEntity(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    NotEnoughBalanceException.class,
    InvalidOperationException.class
  })
  public final ResponseEntity<Object> invalidRequest(Exception ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, new Date());
    body.put(STATUS, HttpStatus.BAD_REQUEST.value());
    body.put(ERRORS, Arrays.asList("Can not perform the Request."));
    logger.info(ERROR_IN_VALIDATION);
    logger.debug(VALIDATION_ERRORS + ex.getMessage());
    return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
      CustomerExistsException.class,
      AccountAlreadyExistsException.class
  })
  public final ResponseEntity<Object> customerExistException(Exception ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, new Date());
    body.put(STATUS, HttpStatus.CONFLICT.value());
    body.put(ERRORS, Arrays.asList(ex.getMessage()));
    logger.info(ERROR_IN_VALIDATION);
    logger.debug(VALIDATION_ERRORS + ex.getMessage());
    return new ResponseEntity(body, HttpStatus.CONFLICT);
  }

}
