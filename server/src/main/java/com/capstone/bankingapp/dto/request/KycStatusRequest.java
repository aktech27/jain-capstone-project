package com.capstone.bankingapp.dto.request;

import lombok.Data;

@Data
public class KycStatusRequest {
  private String email;
  private String phone;
}
