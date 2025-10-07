package com.capstone.bankingapp.controller;

import com.capstone.bankingapp.dto.request.AdminLoginRequest;
import com.capstone.bankingapp.dto.request.CustomerRegisterRequest;
import com.capstone.bankingapp.dto.response.AdminLoginResponse;
import com.capstone.bankingapp.dto.response.ApiResponse;
import com.capstone.bankingapp.dto.response.CustomerRegisterResponse;
import com.capstone.bankingapp.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  @PostMapping("/admin-login")
  public ResponseEntity<ApiResponse<AdminLoginResponse>> login(@RequestBody AdminLoginRequest request) {
    try {
      AdminLoginResponse response = authService.authenticateAdmin(request);
      return ResponseEntity.ok(new ApiResponse<>(true, "Login Successful", response));
    } catch (RuntimeException e) {
      log.error("Runtime Exception : {}", e);
      return ResponseEntity.status(401).body(new ApiResponse<>(false, e.getMessage(), null));
    }
  }

  @PostMapping("/customer-register")
  public ResponseEntity<ApiResponse<CustomerRegisterResponse>> registerCustomer(
      @RequestBody CustomerRegisterRequest request) {
    try {
      CustomerRegisterResponse response = authService.registerCustomer(request);
      return ResponseEntity.ok(new ApiResponse<>(true, "Customer Register Successful", response));
    } catch (RuntimeException e) {
      log.error("Runtime Exception : {}", e);
      return ResponseEntity.status(422).body(new ApiResponse<>(false, e.getMessage(), null));
    }
  }

}
