package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomerDtoToCustomer(CustomerDto customerDto, Customer customer) {

        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());

        return  customer;
    }

    public static CustomerDto mapToCustomerToCustomerDto(Customer customer, CustomerDto customerDto) {

        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());

        return customerDto;
    }

    public static CustomerDetailsDto mapToCustomerToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {

        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());

        return customerDetailsDto;
    }
}
