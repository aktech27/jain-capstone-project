package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
  List<Beneficiary> findByCustomerId(Long customerId);
}
