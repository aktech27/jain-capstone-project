package com.capstone.bankingapp.config;

import com.capstone.bankingapp.model.AdminUser;
import com.capstone.bankingapp.model.AccountType;
import com.capstone.bankingapp.repository.AdminUserRepository;
import com.capstone.bankingapp.repository.AccountTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

  private final PasswordEncoder passwordEncoder;

  @Bean
  CommandLineRunner seedData(AdminUserRepository adminUserRepository, AccountTypeRepository accountTypeRepository) {
    return args -> {

      // Seed Default Admin
      if (adminUserRepository.count() == 0) {
        AdminUser admin = AdminUser.builder()
            .username("AK_ADMIN_01")
            .email("ahamedkbr85@gmail.com")
            .name("Ahamed Kabeer")
            .password(passwordEncoder.encode("Admin@123"))
            .role("ADMIN")
            .build();

        adminUserRepository.save(admin);
        log.info("Default admin created: email={}, password={}", admin.getEmail(), "Admin@123");
      } else {
        log.info("Admin users already exist, skipping admin seeding");
      }

      // Seed Account Types Master
      if (accountTypeRepository.count() == 0) {
        List<AccountType> accountTypes = List.of(
            new AccountType(null, 1001, "SAV", "Savings Account", "Standard savings account", 3.5, true),
            new AccountType(null, 1002, "SAL", "Salary Account", "Salary credit account", 3.0, true),
            new AccountType(null, 1003, "CUR", "Current Account", "For businesses and professionals", 0.0, true),
            new AccountType(null, 1004, "FD", "Fixed Deposit", "Fixed-term deposit with higher interest", 6.5, true),
            new AccountType(null, 1005, "RD", "Recurring Deposit", "Monthly recurring deposit plan", 5.5, true),
            new AccountType(null, 1006, "HLN", "Home Loan", "Loan for housing purposes", 8.0, true),
            new AccountType(null, 1007, "PLN", "Personal Loan", "Personal consumption loan", 10.5, true),
            new AccountType(null, 1008, "VLN", "Vehicle Loan", "Loan for vehicle purchase", 9.0, true));

        accountTypeRepository.saveAll(accountTypes);
        log.info("Seeded {} account types successfully!", accountTypes.size());
      } else {
        log.info("Account types already exist, skipping seeding");
      }
    };
  }
}
