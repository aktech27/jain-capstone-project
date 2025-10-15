package com.capstone.bankingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KycVerifyResponse {
  private Long kycId;
  private boolean isVerified;
  private String rejectReason;
  private String message;
}
