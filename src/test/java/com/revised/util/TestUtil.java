package com.revised.util;

import com.revised.model.Account;
import com.revised.model.AccountType;
import com.revised.model.Customer;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.model.TxStatus;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

  public static List<Account> getAccountList(int size) {
    List<Account> accounts = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Account account = new Account();
      account.setId((long) i);
      account.setBalance(100);
      account.setType(AccountType.CURRENT);
      account.setCustomer(new Customer());
      account.getCustomer().setId((long) i);
      accounts.add(account);
    }
    return accounts;
  }

  public static List<Customer> getCustomers(int size) {
    List<Customer> customers = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Customer customer = new Customer();
      customer.setId((long) i);
      customer.setFirstName("Temp");
      customer.setLastName("Test");
      customer.setEmail("abc");
      customer.setPhoneNumber("1234");
      customers.add(customer);
      customer.setAccountList(new ArrayList<>());
    }
    return customers;
  }

  public static List<Transaction> getTransaction(int size) {
    List<Transaction> transactions = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Transaction transaction = new Transaction();
      transaction.setId((long) i);
      transaction.setType(TransactionType.CREDIT);
      transaction.setAmount(100d);
      transaction.setStatus(TxStatus.SUCCESS);
      transactions.add(transaction);
    }
    return transactions;
  }
}
