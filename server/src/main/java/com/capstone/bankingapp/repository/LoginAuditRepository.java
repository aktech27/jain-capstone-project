package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.LoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginAuditRepository extends JpaRepository<LoginAudit, Long> {
  List<LoginAudit> findByCustomerIdOrderByTimestampDesc(Long customerId);
}