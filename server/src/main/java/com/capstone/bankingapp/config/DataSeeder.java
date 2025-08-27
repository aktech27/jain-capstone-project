package com.capstone.bankingapp.config;

import com.capstone.bankingapp.model.AdminUser;
import com.capstone.bankingapp.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {
  private final PasswordEncoder passwordEncoder;

  @Bean
  CommandLineRunner seedAdmin(AdminUserRepository adminUserRepository) {
    return args -> {
      if (adminUserRepository.count() == 0) {
        AdminUser admin = AdminUser.builder()
            .username("AK_ADMIN_01")
            .email("ahamedkbr85@gmail.com")
            .name("Ahamed Kabeer")
            .password(passwordEncoder.encode("Admin@123"))
            .role("ADMIN")
            .build();

        adminUserRepository.save(admin);
        log.info("Default admin created: email={}, password={}",
            admin.getEmail(), "admin123");
      } else {
        log.info("Admin users already exist, skipping seeding");
      }
    };
  }
}
