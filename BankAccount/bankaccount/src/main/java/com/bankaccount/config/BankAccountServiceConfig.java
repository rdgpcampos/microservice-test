package com.bankaccount.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix="accounts")
@Getter @Setter @ToString
public class BankAccountServiceConfig {
    private String msg;
    private String buildVersion;
    private Map<String, String> mailDetails;
    private List<String> activeBranches;
}
