package com.revised.validation;

public interface IValidator<T> {

  boolean isValid(T object);
}
