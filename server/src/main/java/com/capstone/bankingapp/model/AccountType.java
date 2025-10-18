package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "numeric_code", nullable = false, unique = true)
  private Integer numericCode;

  @Column(name = "code", nullable = false, unique = true, length = 10)
  private String code;

  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;

  @Column(name = "description", length = 200)
  private String description;

  @Column(name = "interest_rate", nullable = false)
  private Double interestRate;

  @Builder.Default
  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;
}
