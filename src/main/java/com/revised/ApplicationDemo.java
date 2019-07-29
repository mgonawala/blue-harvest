package com.revised;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revised.model.Account;
import com.revised.model.Customer;
import com.revised.service.IAccountService;
import com.revised.service.ICustomerService;
import com.revised.validation.AccountBalanceCondition;
import com.revised.validation.AccountTypeNotExistsValidator;
import com.revised.validation.DualValidator;
import com.revised.validation.EnoughAccountBalanceValidator;
import com.revised.validation.TransactionValidCondition;
import com.revised.validation.strategy.CreateAccountValStrategy;
import com.revised.validation.strategy.IValidationStrategy;
import com.revised.validation.strategy.RevertTransactionValidationStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {ApplicationDemo.class, Jsr310Converters.class})
@EnableJpaRepositories(value = {"com.revised.repository"})
@SpringBootApplication
public class ApplicationDemo extends SpringBootServletInitializer {

  private static final Logger logger = LogManager.getLogger(ApplicationDemo.class);

  @Value("${spring.jackson.time-zone}")
  String timeZone;

  @Value("${minimum.initial.credit}")
  Long minimumBalance;

  public static void main(String[] args) {
    SpringApplication.run(ApplicationDemo.class, args);
  }

  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
  }

  @Bean
  public DualValidator enoughBalance() {
    return new EnoughAccountBalanceValidator();
  }

  @Bean
  public TransactionValidCondition transactionValidCondition() {
    return new TransactionValidCondition();
  }

  @Bean
  public DualValidator accountTypeValid() {
    return new AccountTypeNotExistsValidator();
  }

  @Bean
  public DualValidator accountBalanceCondition() {
    return new AccountBalanceCondition(minimumBalance);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  @Qualifier("revertTransaction")
  public IValidationStrategy revertTransactionValidationStrategy() {
    RevertTransactionValidationStrategy strategy = new RevertTransactionValidationStrategy();
    strategy.addRule(enoughBalance());
    return strategy;
  }

  @Bean
  @Qualifier("newTransaction")
  public IValidationStrategy newTransaction() {
    RevertTransactionValidationStrategy strategy = new RevertTransactionValidationStrategy();
    strategy.addRule(transactionValidCondition());
    return strategy;
  }

  @Bean
  @Qualifier("createAccount")
  public IValidationStrategy createAccountValidationStrategy() {
    CreateAccountValStrategy strategy = new CreateAccountValStrategy();
    strategy.addRule(accountTypeValid());
    strategy.addRule(accountBalanceCondition());
    return strategy;
  }

  /*@Bean
  @Profile("dev")
  @ConditionalOnProperty(value = "customers.stub", havingValue = "true", matchIfMissing = false)
  CommandLineRunner runner(ICustomerService customerService, IAccountService accountService) {
    return args -> {
      // read json and write to db
      ObjectMapper mapper = new ObjectMapper();
      TypeReference<List<Customer>> typeReference = new TypeReference<List<Customer>>() {};
      InputStream inputStream = TypeReference.class.getResourceAsStream("/json/customers.json");
      try {
        List<Customer> customer = mapper.readValue(inputStream, typeReference);
        customer.stream().forEach(v -> customerService.createNewCustomer(v));

      } catch (IOException e) {
        System.out.println("Unable to save customers: " + e.getMessage());
      }

      TypeReference<List<Account>> accountTypeReferene = new TypeReference<List<Account>>() {};
      inputStream = TypeReference.class.getResourceAsStream("/json/accounts.json");
      try {
        List<Account> accounts = mapper.readValue(inputStream, accountTypeReferene);
        accounts.stream().forEach(v -> accountService.createNewAccount(v, v.getId()));

      } catch (IOException e) {
        System.out.println("Unable to save Accounts: " + e.getMessage());
      }
    };
  }*/
}
