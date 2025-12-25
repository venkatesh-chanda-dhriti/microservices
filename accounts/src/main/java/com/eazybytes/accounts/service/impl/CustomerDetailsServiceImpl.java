package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.ICustomerDetailsService;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerDetailsServiceImpl implements ICustomerDetailsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow( () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString()));

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);

    CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsToAccountsDto(accounts, new AccountsDto()));
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

    return customerDetailsDto;
    }
}
