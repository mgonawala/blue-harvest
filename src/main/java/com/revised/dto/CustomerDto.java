package com.revised.dto;

import javax.validation.constraints.NotEmpty;

/**
 * Represents DTO class for Customer resource.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
public class CustomerDto {

  /** Firstname of customer * */
  @NotEmpty(message = "First name can't be empty.")
  private String firstName;

  /** Lastname of customer * */
  @NotEmpty(message = "Last name can't be empty.")
  private String lastName;

  /** Phone number of customer * */
  private int phoneNumber;

  /** Email of customer * */
  @NotEmpty private String email;

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

  @Override
  public String toString() {
    return "CustomerDto{"
        + "firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", phoneNumber="
        + phoneNumber
        + ", email='"
        + email
        + '\''
        + '}';
  }
}
