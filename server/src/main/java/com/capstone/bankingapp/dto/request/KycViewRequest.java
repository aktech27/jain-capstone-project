package com.capstone.bankingapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycViewRequest {
  private Long custId;
  private String fileName;
}
