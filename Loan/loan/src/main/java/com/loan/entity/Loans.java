package com.loan.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="loan_number")
    private Long loan_number;

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="start_dt")
    private Date startDt;

    @Column(name="loan_type")
    private String loanType;

    @Column(name="total_loan")
    private int totalLoan;

    @Column(name="amount_paid")
    private int amountPaid;

    @Column(name="outstanding_amount")
    private int outstandingAmount;

    @Column(name="create_dt")
    private LocalDate createDt;

}
