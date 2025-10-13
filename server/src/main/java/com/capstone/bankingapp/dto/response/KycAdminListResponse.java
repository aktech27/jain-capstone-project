package com.capstone.bankingapp.dto.response;

import com.capstone.bankingapp.model.KycDetails.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycAdminListResponse {
  private Long id;
  private String docNumber;
  private String docUrl;
  private DocumentType documentType;
  private Boolean isVerified;
  private String rejectReason;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // Customer Info
  private Long customerId;
  private String customerName;
  private String customerEmail;
  private String customerPhone;
}
