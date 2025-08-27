package com.capstone.bankingapp.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
  @Value("${cors.allowed-origins}")
  private String allowedOrigins;

  @Value("${cors.allowed-methods}")
  private String allowedMethods;

  @Value("${cors.allowed-headers}")
  private String allowedHeaders;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> {
        })
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/auth/**", "/health-check").permitAll()
            .anyRequest().authenticated())
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
    config.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
    config.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));

    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
