package com.revised.controller;

import com.revised.exception.ResourceNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

public class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @Mock private MethodArgumentNotValidException exception;

  @Mock private WebRequest webRequest;

  private HttpHeaders headers = new HttpHeaders();

  @Mock private BindingResult bindingResult;

  private HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

  @Test
  public void testMethodArgumentException() {

    exception = Mockito.mock(MethodArgumentNotValidException.class);
    bindingResult = Mockito.mock(BindingResult.class);
    Mockito.when(exception.getBindingResult()).thenReturn(bindingResult);
    List<FieldError> errors = new ArrayList<>();
    FieldError fieldError = new FieldError("Field", "Invalide", "message");
    errors.add(fieldError);
    Mockito.when(bindingResult.getFieldErrors()).thenReturn(errors);
    ResponseEntity<Object> result =
        globalExceptionHandler.handleMethodArgumentNotValid(
            exception, headers, HttpStatus.BAD_REQUEST, webRequest);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  @Test
  public void testConstraintValidationException() throws IOException {

    DataIntegrityViolationException exception = Mockito.mock(DataIntegrityViolationException.class);
    Mockito.when(exception.getMessage()).thenReturn("Exception Ocurred");
    ResponseEntity<Object> result =
        globalExceptionHandler.constraintViolationException(exception, httpResponse);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  @Test
  public void testResourceNotfoundException() throws IOException {

    ResourceNotFoundException exception = Mockito.mock(ResourceNotFoundException.class);
    Mockito.when(exception.getMessage()).thenReturn("Exception Occurred");
    ResponseEntity<Object> result =
        globalExceptionHandler.handleUserNotFoundException(exception, webRequest);
    Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  @Test
  public void testCustomerNotFoundException() throws IOException {

    ResourceNotFoundException exception = Mockito.mock(ResourceNotFoundException.class);
    Mockito.when(exception.getMessage()).thenReturn("Exception Occurred");
    ResponseEntity<Object> result =
        globalExceptionHandler.customerNotFound(exception, webRequest);
    Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }
}
