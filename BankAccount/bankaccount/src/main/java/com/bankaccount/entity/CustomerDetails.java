package com.bankaccount.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author EazyBytes
 *
 */
@Getter
@Setter
@ToString
public class CustomerDetails {
	
	private List<BankAccount> accounts;
	private List<Loans> loans;
	private List<Cards> cards;

}