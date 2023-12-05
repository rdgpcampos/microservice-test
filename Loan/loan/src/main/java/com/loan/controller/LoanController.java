package com.loan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.loan.entity.Loans;
import com.loan.entity.Properties;
import com.loan.entity.Customer;
import com.loan.config.LoansServiceConfig;
import com.loan.repository.LoanRepository;
import java.util.List;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/loan")
public class LoanController {
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private LoanRepository loanRepository;
    private LoansServiceConfig loansConfig;

    @GetMapping("/{id}")
    private ResponseEntity< List<Loans> > getLoanDetails(@PathVariable Long id) {
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);
        if(loans.size() > 0) {
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } else {
            return null;
        } 
    }

    @PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestHeader("rdgpcampos-correlation-id") String correlationId,@RequestBody Customer customer) {
		logger.info("getLoansDetails() method started");
		List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		logger.info("getLoansDetails() method ended");
		if (loans != null) {
			return loans;
		} else {
			return null;
		}

	}

    @GetMapping("/loans/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(loansConfig.getMsg(), loansConfig.getBuildVersion(),
				loansConfig.getMailDetails(), loansConfig.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return jsonStr;
	}
}
