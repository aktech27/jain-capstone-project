package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
  Optional<AccountType> findByCode(String code);

  Optional<AccountType> findById(Long id);

  Optional<AccountType> findByNumericCode(Integer numericCode);
}
