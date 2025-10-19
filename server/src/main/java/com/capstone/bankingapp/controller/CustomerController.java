package com.capstone.bankingapp.controller;

import com.capstone.bankingapp.dto.request.CustomerOnboardRequest;
import com.capstone.bankingapp.dto.request.CustomerUpdatePasswordRequest;
import com.capstone.bankingapp.dto.response.ApiResponse;
import com.capstone.bankingapp.dto.response.CustomerOnboardResponse;
import com.capstone.bankingapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping("/onboard")
  public ResponseEntity<ApiResponse<CustomerOnboardResponse>> onboardCustomer(
      @RequestBody CustomerOnboardRequest request) {
    try {
      CustomerOnboardResponse response = customerService.onboardCustomer(request);
      return ResponseEntity.ok(new ApiResponse<>(true, "Customer onboarded successfully", response));
    } catch (Exception e) {
      log.error("Error onboarding customer: {}", e.getMessage());
      return ResponseEntity.badRequest()
          .body(new ApiResponse<>(false, e.getMessage(), null));
    }
  }

  @PostMapping("/set-new-password")
  public ResponseEntity<ApiResponse<String>> setNewCustomerPassword(
      @RequestBody CustomerUpdatePasswordRequest request) {
    try {
      String response = customerService.setCustomerPassword(request);
      return ResponseEntity.ok(new ApiResponse<>(true, "Customer password updated successfully", response));
    } catch (Exception e) {
      log.error("Error onboarding customer: {}", e.getMessage());
      return ResponseEntity.badRequest()
          .body(new ApiResponse<>(false, e.getMessage(), null));
    }
  }
}
