package com.capstone.bankingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOnboardResponse {
  private String customerId;
  private String email;
  private String phone;
  private String message;
}