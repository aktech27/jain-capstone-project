package com.capstone.bankingapp.dto.response;

import com.capstone.bankingapp.model.KycDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycStatusResponse {
  private Long id;
  private String docNumber;
  private String docUrl;
  private KycDetails.DocumentType documentType;
  private Boolean isVerified;
  private String rejectReason;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
