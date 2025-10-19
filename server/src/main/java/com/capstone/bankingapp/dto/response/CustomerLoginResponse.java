package com.capstone.bankingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.capstone.bankingapp.model.Customers.CustomerType;

@Data
@AllArgsConstructor
public class CustomerLoginResponse {
  private String token;
  private CustomerDetails customerDetails;

  @Data
  @AllArgsConstructor
  public static class CustomerDetails {
    private Long id;
    private String cif;
    private String custId;
    private boolean isFirstLogin;
    private CustomerType customerType;
  }
}
