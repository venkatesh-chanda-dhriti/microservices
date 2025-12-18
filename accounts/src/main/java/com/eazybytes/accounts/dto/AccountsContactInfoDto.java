package com.eazybytes.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("accounts")
public record AccountsContactInfoDto (String message, Map<String, String> contactDetails, List<String> onCallSupport) {

}
