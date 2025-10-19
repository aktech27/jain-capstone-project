package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.Customers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {
  boolean existsByEmailOrPhone(String email, String phone);

  Optional<Customers> findByCif(String cif);

  Optional<Customers> findByIdAndIsDeletedFalse(Long id);
}
