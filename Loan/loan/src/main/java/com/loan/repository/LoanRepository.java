package com.loan.repository;

import org.springframework.data.repository.CrudRepository;
import com.loan.entity.Loans;
import java.util.List;

public interface LoanRepository extends CrudRepository<Loans, Long> {
    List<Loans> findByCustomerIdOrderByStartDtDesc(Long customerId);
}
