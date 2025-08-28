package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "doc_number", nullable = false, unique = true, length = 30)
  private String docNumber;

  @Column(name = "doc_url", nullable = false, length = 200)
  private String docUrl;

  @Enumerated(EnumType.STRING)
  @Column(name = "doc_type")
  private DocumentType documentType;

  @Builder.Default
  @Column(name = "is_verified", nullable = false)
  private Boolean isVerified = false;

  @Column(name = "reject_reason", columnDefinition = "TEXT")
  private String rejectReason;

  @Builder.Default
  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_information_id", nullable = false)
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

  public enum DocumentType {
    AADHAAR, PAN, PHOTO
  }
}
