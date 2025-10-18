package com.capstone.bankingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "login_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customers customer;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "success", nullable = false)
  private Boolean success;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "user_agent")
  private String userAgent;

  @Column(name = "timestamp", updatable = false)
  private LocalDateTime timestamp;

  @PrePersist
  protected void onCreate() {
    this.timestamp = LocalDateTime.now();
  }
}
