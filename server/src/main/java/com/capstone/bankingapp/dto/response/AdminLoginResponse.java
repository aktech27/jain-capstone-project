package com.capstone.bankingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminLoginResponse {
  private String token;
  private UserDetailsResponse userDetails;

  @Data
  @AllArgsConstructor
  public static class UserDetailsResponse {
    private Long id;
    private String username;
    private String email;
    private String name;
  }
}
