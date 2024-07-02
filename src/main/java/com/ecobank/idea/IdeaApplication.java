package com.ecobank.idea;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@OpenAPIDefinition(
        info = @Info(
                title = "Idea portal API documentation",
                description = "A portal to share ideas on different challenges faced by the bank",
                version = "v1",
                contact = @Contact(
                        name = "Innovation / Technology",
                        email = "innovation@ecobank.com"
                ),
                license = @License(
                        name = "Apache 2.0"
                )
        )
)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableJpaRepositories("com.ecobank.idea.repository")
@EnableGlobalMethodSecurity(prePostEnabled = true)          // Method level authorization
public class IdeaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdeaApplication.class, args);
    }

}
