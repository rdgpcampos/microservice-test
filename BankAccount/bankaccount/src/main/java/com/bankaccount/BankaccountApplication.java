package com.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import lombok.AllArgsConstructor;

//@EnableJpaRepositories("com.bankaccount.repository")
//@EntityScan("com.bankaccount.entity")
@SpringBootApplication
@AllArgsConstructor
@RefreshScope
@EnableFeignClients
@ComponentScan("com.bankaccount.controller")
@ComponentScan("com.bankaccount.config")
public class BankaccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankaccountApplication.class, args);
	}

}
