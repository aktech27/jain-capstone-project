package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customers {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "customer_id", nullable = false, unique = true, length = 50)
  private String customerId;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false, unique = true, length = 10)
  private String phone;

  @Column(length = 11)
  private String cif;

  @Builder.Default
  @Column(name = "is_first_login")
  private Boolean isFirstLogin = true;

  @Enumerated(EnumType.STRING)
  @Column(name = "customer_type")
  private CustomerType customerType;

  @Builder.Default
  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customer_information_id", referencedColumnName = "id")
  private CustomerInformation customerInformation;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public enum CustomerType {
    INDIVIDUAL, CORPORATE
  }

}
