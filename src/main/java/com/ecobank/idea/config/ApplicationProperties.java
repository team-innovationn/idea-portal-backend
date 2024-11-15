package com.ecobank.idea.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class ApplicationProperties {
    private List<String> allowedOrigins;
    private String googleClientId;
    private String googleClientSecret;
    private String applicationName;
    private String applicationEmail;
    private String baseUrl;
    private String loginPageUrl;
    private String loginSuccessUrl;
    private String loginFailureUrl;
    private String adminUserEmail;
    private String adminUserPassword;
}