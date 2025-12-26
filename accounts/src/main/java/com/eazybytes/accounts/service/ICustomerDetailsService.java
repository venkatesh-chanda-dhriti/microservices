package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDto;

public interface ICustomerDetailsService {

    public CustomerDetailsDto fetchCustomerDetails (String mobileNumber, String correlationId);
}
