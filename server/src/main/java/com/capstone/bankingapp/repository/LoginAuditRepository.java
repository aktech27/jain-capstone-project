package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.LoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoginAuditRepository extends JpaRepository<LoginAudit, Long> {
  List<LoginAudit> findByCustomerIdOrderByTimestampDesc(Long customerId);
}