package com.dt8.task_flow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/auth/**").permitAll() // Allow all requests to /auth/**
                        .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER") // Require ADMIN or USER role for /api/**
                        .anyRequest().authenticated()
                )
                // This line configures session management to be stateless. This means that the server doesn't maintain any user session data on the server side. This is important for REST APIs
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // This line disables CSRF (Cross-Site Request Forgery) protection. CSRF protection is typically used to prevent malicious requests from untrusted sources, but it might not be necessary in a purely RESTful API. However, you should carefully consider the security implications before disabling it.
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
