package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name can not be a null or empty ")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email must to be valid")
    private String email;

    @NotEmpty(message = "MobileNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
