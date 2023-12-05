package com.bankaccount.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.bankaccount.entity.Customer;
import com.bankaccount.entity.Loans;

@FeignClient("loans")
public interface LoansFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/loan/myLoans", consumes = "application/json")
    List<Loans> getLoanDetails(@RequestHeader("rdgpcampos-correlation-id") String correlationId, @RequestBody Customer customer);
}
