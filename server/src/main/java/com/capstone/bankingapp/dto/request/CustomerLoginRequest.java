package com.capstone.bankingapp.dto.request;

import lombok.Data;

@Data
public class CustomerLoginRequest {
  private String cif;
  private String password;
}