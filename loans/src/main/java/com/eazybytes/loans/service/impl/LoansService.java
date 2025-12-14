package com.eazybytes.loans.service.impl;

import com.eazybytes.loans.constant.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistsException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoansService  implements ILoansService {

    @Autowired
    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalLoan = loansRepository.findByMobileNumber(mobileNumber);

        if(optionalLoan.isPresent())
                throw(new LoanAlreadyExistsException("Loan already registered with given mobileNumber: "+mobileNumber));

        loansRepository.save(createNewLoan(mobileNumber));
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {

       Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loans", "MobileNumber", mobileNumber));

       LoansDto loansDto = LoansMapper.mapToLoanToLoanDto(loans, new LoansDto());

       return loansDto;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {

        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber())
                .orElseThrow(() ->new ResourceNotFoundException("Loan", "MobileNumber", loansDto.getMobileNumber()));

        LoansMapper.mapToLoanDtoToLoan(loansDto, loans);
        loansRepository.save(loans);

        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->new ResourceNotFoundException("Loans", "MobileNumber", mobileNumber));

        loansRepository.deleteById(loans.getLoanId());

        return true;
    }

    private Loans createNewLoan(String mobileNumber) {

        Loans loans = new Loans();
        Long loanNumber = 1000000000L+ new Random().nextInt(900000000);

        loans.setLoanNumber(Long.toString(loanNumber));
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        loans.setMobileNumber(mobileNumber);
        loans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);

        return loans;
    }
}
