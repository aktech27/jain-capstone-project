package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  List<Account> findByCustomerIdAndIsActiveTrue(Long customerId);

  Account findByAccountNumber(String accountNumber);
}