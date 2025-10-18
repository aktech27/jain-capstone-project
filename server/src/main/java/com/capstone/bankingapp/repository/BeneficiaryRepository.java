package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
  List<Beneficiary> findByCustomerId(Long customerId);
}
