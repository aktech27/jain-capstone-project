package com.capstone.bankingapp.dto.request;

import lombok.Data;

@Data
public class CustomerUpdatePasswordRequest {
  private String cif;
  private String newPassword;
}
