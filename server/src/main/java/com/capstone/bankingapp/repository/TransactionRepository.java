package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
