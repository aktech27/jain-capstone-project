package com.capstone.bankingapp.dto.request;

import lombok.Data;

@Data
public class KycVerifyRequest {
  private Long kycId;
  private boolean verified;
  private String rejectReason;
}
