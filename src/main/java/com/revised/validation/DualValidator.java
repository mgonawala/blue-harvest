package com.revised.validation;

public interface DualValidator<T, V> {

  boolean isValid(T object1, V object2);
}
