package com.revised.dto;

import javax.validation.constraints.NotEmpty;

public class CustomerDto {

  @NotEmpty(message = "First name can't be empty.")
  private String firstName;

  @NotEmpty(message = "Last name can't be empty.")
  private String lastName;

  private int phoneNumber;

  @NotEmpty
  private String email;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(int phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
