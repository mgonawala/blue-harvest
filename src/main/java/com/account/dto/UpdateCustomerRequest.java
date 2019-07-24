package com.account.dto;

import java.util.Optional;

public class UpdateCustomerRequest {

  private Optional<String> firstName = Optional.empty();

  private Optional<String> lastName = Optional.empty();

  public Optional<String> getFirstName() {
    return firstName;
  }

  public void setFirstName(Optional<String> firstName) {
    this.firstName = firstName;
  }

  public Optional<String> getLastName() {
    return lastName;
  }

  public void setLastName(Optional<String> lastName) {
    this.lastName = lastName;
  }
}
