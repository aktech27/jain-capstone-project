package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @Column(name = "transaction_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "balance_after_txn", nullable = false)
  private Double balanceAfterTxn;

  @Column(name = "description")
  private String description;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  public enum TransactionType {
    DEBIT, CREDIT
  }
}
