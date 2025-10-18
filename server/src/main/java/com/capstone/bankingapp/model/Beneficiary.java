package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "beneficiaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beneficiary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customers customer;

  @Column(name = "beneficiary_name", nullable = false)
  private String name;

  @Column(name = "beneficiary_account_number", nullable = false, length = 20)
  private String accountNumber;

  @Column(name = "ifsc_code", nullable = false, length = 11)
  private String ifscCode;

  @Column(name = "added_at", updatable = false)
  private LocalDateTime addedAt;

  @PrePersist
  protected void onAdd() {
    this.addedAt = LocalDateTime.now();
  }
}
