package com.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@EntityScan(basePackageClasses = {
		AccountApplication.class,
		Jsr310Converters.class
})
@EnableJpaRepositories(value = {"com.account.repository"})
@SpringBootApplication
public class AccountApplication {

	@Value("${spring.jackson.time-zone}")
	String timeZone ;

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@PostConstruct
	void init(){
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
	}

}
