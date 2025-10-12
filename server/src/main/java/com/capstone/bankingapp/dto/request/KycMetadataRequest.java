package com.capstone.bankingapp.dto.request;

import lombok.Data;

@Data
public class KycMetadataRequest {
  private Long custId;
  private String fileType;
  private String docNumber;
}
