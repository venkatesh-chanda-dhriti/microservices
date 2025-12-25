package com.eazybytes.accounts.dto;

import lombok.Data;

@Data
public class CustomerDetailsDto {

    private String email;

    private String mobileNumber;

    private String name;

    private AccountsDto accountsDto;

    private LoansDto loansDto;

    private CardsDto cardsDto;
}
