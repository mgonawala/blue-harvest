package com.account.util;

import com.account.dto.AccountDTO;
import com.account.dto.CreateAccountRequest;
import com.account.dto.CreateCustomerRequest;
import com.account.dto.CreateTransactionRequest;
import com.account.dto.CustomerDTO;
import com.account.dto.TransactionDTO;
import com.account.model.Account;
import com.account.model.Customer;
import com.account.model.Transaction;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseParser {

  public static List<TransactionDTO> parseTransactions(List<Transaction> transactions) {
    return transactions.stream()
        .map(ResponseParser::parseTransactions)
        .collect(Collectors.toList());
  }

  public static List<CustomerDTO> parseCustomers(List<Customer> customerList) {
    return customerList.stream().map(ResponseParser::parseCustomers).collect(Collectors.toList());
  }

  public static List<AccountDTO> parseAccounts(List<Account> accountList) {
    return accountList.stream().map(ResponseParser::parseAccounts).collect(Collectors.toList());
  }

  public static TransactionDTO parseTransactions(Transaction transaction) {
    return new TransactionDTO(transaction);
  }

  public static AccountDTO parseAccounts(Account account) {

    AccountDTO dto = new AccountDTO();
    dto.setAccountBalance(account.getAccountBalance());
    dto.setAccountNumber(account.getAccountNumber());
    dto.setAccountType(account.getAccountType());
    return dto;
  }

  public static CustomerDTO parseCustomers(Customer customer) {

    CustomerDTO dto = new CustomerDTO();
    dto.setFirstName(customer.getFirstName());
    dto.setLastName(customer.getLastName());
    dto.setAccounts(Collections.unmodifiableList(customer.getAccounts()));
    dto.setCustomerID(customer.getCustomerID());
    return dto;
  }

  public static Customer parseCreateCustomerRequest(CreateCustomerRequest createCustomerRequest) {
    Customer customer = new Customer();
    customer.setFirstName(createCustomerRequest.getFirstName());
    customer.setLastName(createCustomerRequest.getLastName());
    return customer;
  }

  public static Account parseCreateAccountRequest(CreateAccountRequest createAccountRequest) {
    Account account = new Account();
    account.setAccountType(createAccountRequest.getAccountType());
    account.setAccountBalance(createAccountRequest.getAccountBalance());
    return account;
  }

  public static Transaction parseCreateTransaction(
      CreateTransactionRequest createTransactionRequest) {
    return new Transaction(createTransactionRequest);
  }
}
