package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "customer_information")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInformation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "phone", nullable = false, unique = true)
  private String phone;

  @Column(name = "dob", nullable = false)
  private LocalDate dob;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", nullable = false)
  private Gender gender;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "current_address", columnDefinition = "json")
  private String currentAddress;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "permanent_address", columnDefinition = "json")
  private String permanentAddress;

  @Column(name = "nominee_name")
  private String nomineeName;

  @Column(name = "nominee_relation")
  private String nomineeRelation;

  @Column(name = "nominee_phone")
  private String nomineePhone;

  @OneToOne(mappedBy = "customerInformation", fetch = FetchType.LAZY)
  private Customers customer;

  @OneToMany(mappedBy = "customerInformation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default
  private List<KycDetails> kycDetailsList = new ArrayList<>();

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public enum Gender {
    MALE, FEMALE
  }
}
