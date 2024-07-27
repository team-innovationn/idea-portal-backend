package com.ecobank.idea.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

// Bean responsible for configuring security of our application
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth.requestMatchers(API_BASE_URL + "/auth/**").permitAll().requestMatchers(API_BASE_URL + "/ideas").permitAll().requestMatchers(API_BASE_URL + "/users").permitAll().requestMatchers(API_BASE_URL + "/idea/**").permitAll().requestMatchers(API_BASE_URL + "/challenges").denyAll().requestMatchers(API_BASE_URL + "/challenge").permitAll().requestMatchers(API_BASE_URL + "/sse/subscribe").denyAll()

                        // Permit documentation requests
                        .requestMatchers("/docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll().anyRequest().authenticated())

                // Handle exceptions with security filters
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
                    // Set the response headers
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    // Write the JSON response
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write("{" + "\"apiPath\": " + request.getRequestURI() + "," + "\"errorCode\": " + HttpStatus.UNAUTHORIZED + "," + "\"errorMessage\": \"Invalid access token\"," + "\"errorTime\": " + LocalDateTime.now() + "}");
                }))

                // Don't store session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Provide Authentication handler
                .authenticationProvider(authenticationProvider)

                // Add custom filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
