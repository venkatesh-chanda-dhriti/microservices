package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto contactInfoDto;

    @Autowired
    private IAccountsService iAccountsService;

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        iAccountsService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetailsByMobileNumber(@RequestParam
                                                                             @Pattern(regexp="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
                                                                             String mobileNumber) {

        CustomerDto customerDto = iAccountsService.findByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody

                                                                CustomerDto customerDto) {

        boolean isAccountUpdated = iAccountsService.updateAccount(customerDto);

        if(isAccountUpdated)
           return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                                @Pattern(regexp="(^$|[0-9]{10})",message = "MobileNumber must be 10 digits")
                                                                String mobileNumber) {

        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);

        if(isDeleted)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
         else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    }

    @Retry(name="getBuildVersion", fallbackMethod = "getBuildVersionFallBack")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        logger.debug("Invoked getBuildVersion method");
        //throw  new RuntimeException();
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    public ResponseEntity<String> getBuildVersionFallBack(Throwable throwable) {
        logger.debug("Invoked getBuildVersionFallBack method");
        return ResponseEntity.status(HttpStatus.OK).body("0.9");
    }

    @RateLimiter(name="getJavaVersion", fallbackMethod = "getJavaVersionFallBack")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        logger.debug("Invoked getJavaVersion method");
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    public ResponseEntity<String> getJavaVersionFallBack(Throwable throwable) {
        logger.debug("Invoked getJavaVersionFallBack method");
        return ResponseEntity.status(HttpStatus.OK).body("Java 25");
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(contactInfoDto);
    }
}
