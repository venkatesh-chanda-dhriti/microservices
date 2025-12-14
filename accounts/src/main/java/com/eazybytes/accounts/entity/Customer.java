package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="customer_new")
@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class Customer  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    private String name;

    private String email;

    private String mobileNumber;
}
