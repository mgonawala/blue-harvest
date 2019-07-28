package com.revised.validation.strategy;

import com.revised.validation.DualValidator;

public interface IValidationStrategy<T, V> {

  void addRule(DualValidator validator);

  boolean isValid(T t, V v);
}
