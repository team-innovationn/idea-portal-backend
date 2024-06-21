package com.ecobank.idea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableJpaRepositories("com.ecobank.idea.repository")
@EnableGlobalMethodSecurity(prePostEnabled = true)          // Method level authorization
public class IdeaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdeaApplication.class, args);
    }

}
