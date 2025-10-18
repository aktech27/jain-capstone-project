package com.capstone.bankingapp.service;

import com.capstone.bankingapp.dto.request.AdminLoginRequest;
import com.capstone.bankingapp.dto.request.CustomerRegisterRequest;
import com.capstone.bankingapp.dto.request.CustomerRegisterRequest.AddressDetails;
import com.capstone.bankingapp.dto.request.CustomerRegisterRequest.BasicInfo;
import com.capstone.bankingapp.dto.request.CustomerRegisterRequest.NomineeDetails;
import com.capstone.bankingapp.dto.response.AdminLoginResponse;
import com.capstone.bankingapp.dto.response.CustomerRegisterResponse;
import com.capstone.bankingapp.model.AdminUser;
import com.capstone.bankingapp.model.CustomerInformation;
import com.capstone.bankingapp.repository.AdminUserRepository;
import com.capstone.bankingapp.repository.CustomerInformationRepository;
import com.capstone.bankingapp.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final AdminUserRepository adminUserRepository;
  private final CustomerInformationRepository customerInformationRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final ObjectMapper objectMapper;

  public AdminLoginResponse authenticateAdmin(AdminLoginRequest request) {
    Optional<AdminUser> userOpt = adminUserRepository.findByUsername(request.getUsername());
    log.info("Admin login for username: {}", request.getUsername());

    if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
      log.warn("Invalid login attempt for username: {}", request.getUsername());
      throw new RuntimeException("Invalid username or password");
    }

    AdminUser user = userOpt.get();
    log.info("Login successful: {}", user);

    Map<String, Object> payload = new HashMap<>();
    payload.put("id", user.getId());
    payload.put("email", user.getEmail());

    String token = jwtUtil.generateToken(payload);
    log.debug("Token generated : {}", token);

    AdminLoginResponse.UserDetailsResponse userDetails = new AdminLoginResponse.UserDetailsResponse(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getName());

    return new AdminLoginResponse(token, userDetails);
  }

  public CustomerRegisterResponse registerCustomer(CustomerRegisterRequest request) {
    BasicInfo basicInfo = request.getBasicInfo();
    AddressDetails addressDetails = request.getAddressDetails();
    NomineeDetails nomineeDetails = request.getNomineeDetails();

    log.debug("Customer register with {} and {}", basicInfo.getEmail(), basicInfo.getPhone());

    Optional<CustomerInformation> custInfoByEmail = customerInformationRepository.findByEmail(basicInfo.getEmail());

    if (custInfoByEmail.isPresent()) {
      log.warn("Invalid Email, already registered: {}", basicInfo.getEmail());
      throw new RuntimeException("Email already registered");
    }

    Optional<CustomerInformation> custInfoByPhone = customerInformationRepository.findByPhone(basicInfo.getPhone());
    if (custInfoByPhone.isPresent()) {
      log.warn("Invalid Phone, already registered: {}", basicInfo.getPhone());
      throw new RuntimeException("Phone number already registered");
    }

    CustomerInformation newCustomer = new CustomerInformation();
    newCustomer.setName(basicInfo.getName());
    newCustomer.setEmail(basicInfo.getEmail());
    newCustomer.setPhone(basicInfo.getPhone());
    newCustomer.setDob(basicInfo.getDob());
    newCustomer.setGender(basicInfo.getGender());
    newCustomer.setCustomerType(basicInfo.getCustomerType());

    newCustomer.setNomineeName(nomineeDetails.getName());
    newCustomer.setNomineePhone(nomineeDetails.getPhone());
    newCustomer.setNomineeRelation(nomineeDetails.getRelation());

    try {
      newCustomer.setCurrentAddress(objectMapper.writeValueAsString(addressDetails.getCurrent()));
      newCustomer.setPermanentAddress(objectMapper.writeValueAsString(addressDetails.getPermanant()));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize JSON fields", e);
    }

    log.info("Customer details : {}", newCustomer);
    customerInformationRepository.save(newCustomer);
    log.info("Customer registered successfully with ID: {}", newCustomer.getId());

    return new CustomerRegisterResponse(newCustomer.getId());
  }
}
