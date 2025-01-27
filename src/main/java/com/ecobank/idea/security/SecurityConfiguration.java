package com.ecobank.idea.security;

import com.ecobank.idea.config.ApplicationProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final EmailTokenAuthenticationProvider emailTokenAuthenticationProvider;
    private final ApplicationProperties applicationProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(API_BASE_URL + "/auth/**").permitAll()
                        .requestMatchers(API_BASE_URL + "/ideas").permitAll()
                        .requestMatchers(API_BASE_URL + "/users").permitAll()
                        .requestMatchers(API_BASE_URL + "/idea/**").permitAll()
                        .requestMatchers(API_BASE_URL + "/challenge").permitAll()
                        .requestMatchers(API_BASE_URL + "/sse/subscribe").denyAll()
                        .requestMatchers(API_BASE_URL + "/challenges").denyAll()

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

                .formLogin(form -> form.disable())

                .authenticationProvider(emailTokenAuthenticationProvider)

                // Add custom filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
