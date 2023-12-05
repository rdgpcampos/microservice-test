package com.bankaccount.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import com.bankaccount.entity.BankAccount;
import com.bankaccount.entity.Customer;
import com.bankaccount.entity.CustomerDetails;
import com.bankaccount.entity.Properties;
import com.bankaccount.entity.Loans;
import com.bankaccount.entity.Cards;
import java.util.List;
import com.bankaccount.repository.BankAccountRepository;
import com.bankaccount.service.client.CardsFeignClient;
import com.bankaccount.service.client.LoansFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.bankaccount.config.BankAccountServiceConfig;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/bankAccount")
public class BankAccountController {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);
    private BankAccountRepository bankAccountRepository;
    private BankAccountServiceConfig bankAccountServiceConfig;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;

    @GetMapping("/myAccount/{id}")
    public ResponseEntity<BankAccount> getAccountDetails(@PathVariable Long id) {
        BankAccount bankAccount = bankAccountRepository.findByCustomerId(id).get(0);
        if(bankAccount != null) {
            return new ResponseEntity<>(bankAccount,HttpStatus.OK);
        } else {
            return null;
        }
    }

    @GetMapping("/properties")
    public String getProperties() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(
                                            bankAccountServiceConfig.getMsg(),
                                            bankAccountServiceConfig.getBuildVersion(),
                                            bankAccountServiceConfig.getMailDetails(),
                                            bankAccountServiceConfig.getActiveBranches()
                                            );
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    @PostMapping("/myCustomerDetails")
    @CircuitBreaker(name="detailsForCustomerSupportApp",fallbackMethod="myCustomerDetailsFallBack")
    @Retry(name="retryForCustomerDetails",fallbackMethod="myCustomerDetailsFallBack")
    public CustomerDetails myCustomerDetails(@RequestHeader("rdgpcampos-correlation-id") String correlationId,@RequestBody Customer customer) {
		logger.info("myCustomerDetails() method started");
        List<BankAccount> accounts = bankAccountRepository.findByCustomerId(customer.getCustomerId());
		List<Loans> loans = loansFeignClient.getLoanDetails(correlationId, customer);
		List<Cards> cards = cardsFeignClient.getCardDetails(correlationId, customer);

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loans);
		customerDetails.setCards(cards);
		logger.info("myCustomerDetails() method ended");
		return customerDetails;

	}

    private CustomerDetails myCustomerDetailsFallBack(@RequestHeader("rdgpcampos-correlation-id") String correlationId, Customer customer, Throwable t) {
		List<BankAccount> accounts = bankAccountRepository.findByCustomerId(customer.getCustomerId());
		List<Loans> loans = loansFeignClient.getLoanDetails(correlationId, customer);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loans);
		logger.info("myCustomerDetails() method ended");
		return customerDetails;
	}
	
	@GetMapping("/sayHello")
	@RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
	public String sayHello() {
		return "Hello, Welcome to EazyBank";
	}

	private String sayHelloFallback(Throwable t) {
		return "Hi, Welcome to EazyBank";
	}
}
