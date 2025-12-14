package com.eazybytes.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class Accounts extends BaseEntity{

    @Id
    private Long accountNumber;
    private Long customerId;
    private String branchAddress;
    private String accountType;
}
