package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Cards",
        description = "Schema to hold Card information"
)
@Data
public class LoansDto {

    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    @Schema(
            description = "Mobile Number of Customer", example = "4354437687"
    )
    private String mobileNumber;

    @NotEmpty(message = "Loan Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    @Schema(
            description = "Loan Number of the customer", example = "100646930341"
    )
    private String LoanNumber;

    @NotEmpty(message = "LoanType can not be a null or empty")
    @Schema(
            description = "Type of the Loan", example = "Personal Loan"
    )
    private String LoanType;

    @PositiveOrZero(message = "total Loan used should be equal or greater than zero")
    @Schema(
            description = "total Loan available against a card", example = "100000"
    )
    private int totalLoan;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    @Schema(
            description = "Total amount used by a Customer", example = "1000"
    )
    private int amountPaid;

    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    @Schema(
            description = "Total available amount against a card", example = "90000"
    )
    private int outstandingAmount;

}