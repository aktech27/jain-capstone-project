package com.capstone.bankingapp.repository;

import com.capstone.bankingapp.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

  Optional<AdminUser> findByUsername(String username);

  Optional<AdminUser> findByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
