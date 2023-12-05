package com.bankaccount.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.bankaccount.entity.Customer;
import com.bankaccount.entity.Cards;

@FeignClient("cards")
public interface CardsFeignClient {
    @RequestMapping(method = RequestMethod.POST, value="myCards", consumes="application/json")
    List<Cards> getCardDetails(@RequestHeader("rdgpcampos-correlation-id") String correlationId, @RequestBody Customer customer);
}
