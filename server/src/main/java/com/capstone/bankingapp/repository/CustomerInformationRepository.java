package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.CustomerInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerInformationRepository extends JpaRepository<CustomerInformation, Long> {
  Optional<CustomerInformation> findByPhone(String phone);

  Optional<CustomerInformation> findByEmail(String email);

  Optional<CustomerInformation> findById(Long id);

  boolean existsByPhone(String phone);

  boolean existsByEmail(String email);

  Optional<CustomerInformation> findByEmailOrPhone(String email, String phone);
}
