package com.eazybytes.loans.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loans_new")
@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class Loans extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private String mobileNumber;

    private String loanNumber;

    private String loanType;

    private int totalLoan;

    private int amountPaid;

    private int outstandingAmount;
}
