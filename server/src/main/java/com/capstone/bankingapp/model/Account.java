package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_number", nullable = false, unique = true, length = 20)
  private String accountNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customers customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_type_id", nullable = false)
  private AccountType accountType;

  @Column(name = "balance", nullable = false)
  private Double balance;

  @Builder.Default
  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
