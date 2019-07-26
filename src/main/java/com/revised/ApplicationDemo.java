package com.revised;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revised.model.Account;
import com.revised.model.Customer;
import com.revised.service.IAccountService;
import com.revised.service.ICustomerService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {ApplicationDemo.class, Jsr310Converters.class})
@EnableJpaRepositories(value = {"com.revised.repository"})
@SpringBootApplication
public class ApplicationDemo extends SpringBootServletInitializer {

  @Value("${spring.jackson.time-zone}")
  String timeZone;

  public static void main(String[] args) {
    SpringApplication.run(ApplicationDemo.class, args);
  }

  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
  }

  @Bean
  @ConditionalOnProperty(value = "customers.stub",
      havingValue = "true",
      matchIfMissing = false)
  CommandLineRunner runner(ICustomerService customerService, IAccountService accountService) {
    return args -> {
      // read json and write to db
      ObjectMapper mapper = new ObjectMapper();
      TypeReference<List<Customer>> typeReference = new TypeReference<List<Customer>>() {
      };
      InputStream inputStream = TypeReference.class.getResourceAsStream("/json/customers.json");
      try {
        List<Customer> customer = mapper.readValue(inputStream, typeReference);
        customer.stream().forEach(v -> customerService.createNewCustomer(v));

      } catch (IOException e) {
        System.out.println("Unable to save customers: " + e.getMessage());
      }

      TypeReference<List<Account>> accountTypeReferene = new TypeReference<List<Account>>() {
      };
      inputStream = TypeReference.class.getResourceAsStream("/json/accounts.json");
      try {
        List<Account> accounts = mapper.readValue(inputStream, accountTypeReferene);
        accounts.stream().forEach(v -> accountService.createNewAccount(v, v.getId()));

      } catch (IOException e) {
        System.out.println("Unable to save Accounts: " + e.getMessage());
      }
    };
  }
}
