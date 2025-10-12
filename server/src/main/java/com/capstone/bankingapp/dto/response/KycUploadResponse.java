package com.capstone.bankingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycUploadResponse {
  private Long docId;
  private String filename;
  private String fileType;
  private Long custId;
}
