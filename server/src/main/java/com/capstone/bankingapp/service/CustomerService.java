package com.capstone.bankingapp.service;

import com.capstone.bankingapp.dto.request.CustomerOnboardRequest;
import com.capstone.bankingapp.dto.response.CustomerOnboardResponse;
import com.capstone.bankingapp.model.CustomerInformation;
import com.capstone.bankingapp.model.Customers;
import com.capstone.bankingapp.model.KycDetails;
import com.capstone.bankingapp.repository.CustomerInformationRepository;
import com.capstone.bankingapp.repository.CustomersRepository;
import com.capstone.bankingapp.repository.KycRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

  private final CustomerInformationRepository customerInformationRepository;
  private final CustomersRepository customersRepository;
  private final KycRepository kycRepository;
  private final PasswordEncoder passwordEncoder;

  public CustomerOnboardResponse onboardCustomer(CustomerOnboardRequest request) {
    Long infoId = request.getCustomerInformationId();
    log.info("Starting onboarding for customerInformationId={}", infoId);

    CustomerInformation customerInfo = customerInformationRepository.findById(infoId)
        .orElseThrow(() -> new RuntimeException("Customer information not found"));

    List<KycDetails> kycDocs = kycRepository.findByCustomerInformationIdAndIsDeletedFalse(infoId);
    boolean hasVerifiedKyc = kycDocs.stream().anyMatch(k -> Boolean.TRUE.equals(k.getIsVerified()));

    if (!hasVerifiedKyc) {
      throw new RuntimeException("Customer KYC not yet verified. Cannot onboard customer.");
    }

    if (customersRepository.existsByEmailOrPhone(customerInfo.getEmail(), customerInfo.getPhone())) {
      throw new RuntimeException("Customer already onboarded.");
    }

    String namePart = customerInfo.getName().replaceAll("\\s+", "")
        .substring(0, Math.min(4, customerInfo.getName().length())).toLowerCase();

    String aadhaarNumber = kycDocs.stream()
        .filter(k -> k.getDocumentType() == KycDetails.DocumentType.AADHAAR)
        .map(KycDetails::getDocNumber)
        .findFirst()
        .orElse("000000000000");

    String aadhaarPart = aadhaarNumber.substring(0, 4);
    String phonePart = customerInfo.getPhone().substring(customerInfo.getPhone().length() - 4);

    String tempPasswordRaw = namePart + aadhaarPart + phonePart;
    String encodedPassword = passwordEncoder.encode(tempPasswordRaw);

    log.debug("Generated Password : {}", tempPasswordRaw);

    Customers customer = Customers.builder()
        .customerId("C271100" + customerInfo.getId().toString())
        .cif("271100" + aadhaarPart + customerInfo.getId().toString())
        .password(encodedPassword)
        .email(customerInfo.getEmail())
        .phone(customerInfo.getPhone())
        .customerType(Customers.CustomerType.valueOf(customerInfo.getCustomerType().name()))
        .customerInformation(customerInfo)
        .build();

    customersRepository.save(customer);

    log.info("Customer onboarded successfully with ID: {}", customer.getCustomerId());

    return new CustomerOnboardResponse(
        customer.getCustomerId(),
        customer.getEmail(),
        customer.getPhone(),
        "Customer onboarded successfully. Temporary password generated internally.");
  }
}
