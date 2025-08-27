package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(length = 100)
  private String name;

  @Column(nullable = false, length = 20)
  private String role;

  @Builder.Default
  @Column(name = "is_2fa_enabled", nullable = false)
  private boolean is2FaEnabled = false;

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
