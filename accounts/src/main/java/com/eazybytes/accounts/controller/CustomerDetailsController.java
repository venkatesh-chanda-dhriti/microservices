package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.service.ICustomerDetailsService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerDetailsController {

    @Autowired
    private ICustomerDetailsService service;

    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam
                                                                       @Pattern(regexp="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
                                                                       String mobileNumber) {

        CustomerDetailsDto customerDetailsDto = service.fetchCustomerDetails(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
