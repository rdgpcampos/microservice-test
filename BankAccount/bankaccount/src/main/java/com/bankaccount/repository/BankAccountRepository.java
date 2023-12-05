package com.bankaccount.repository;

import org.springframework.data.repository.CrudRepository;
import com.bankaccount.entity.BankAccount;
import java.util.List;

public interface BankAccountRepository extends CrudRepository<BankAccount,Long> {
    List<BankAccount> findByCustomerId(Long customerId);
}
