package com.capstone.bankingapp.service;

import com.capstone.bankingapp.dto.request.KycMetadataRequest;
import com.capstone.bankingapp.dto.request.KycVerifyRequest;
import com.capstone.bankingapp.dto.response.KycAdminListResponse;
import com.capstone.bankingapp.dto.response.KycListResponse;
import com.capstone.bankingapp.dto.response.KycStatusResponse;
import com.capstone.bankingapp.dto.response.KycVerifyResponse;
import com.capstone.bankingapp.model.CustomerInformation;
import com.capstone.bankingapp.model.KycDetails;
import com.capstone.bankingapp.repository.CustomerInformationRepository;
import com.capstone.bankingapp.repository.KycRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KycService {

  private final KycRepository kycRepository;
  private final CustomerInformationRepository customerInformationRepository;

  private final Path uploadDir = Paths.get("uploads/").toAbsolutePath().normalize();

  public void init() throws IOException {
    Files.createDirectories(uploadDir);
  }

  public KycDetails saveKycDocument(KycMetadataRequest metadata, MultipartFile file) throws IOException {
    CustomerInformation customer = customerInformationRepository.findById(metadata.getCustId())
        .orElseThrow(() -> new RuntimeException("Customer not found"));

    Path customerDir = uploadDir.resolve("cust_" + metadata.getCustId());
    Files.createDirectories(customerDir);

    String originalFilename = file.getOriginalFilename();
    String extension = "";
    if (originalFilename != null && originalFilename.contains(".")) {
      extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    }
    String randomFilename = UUID.randomUUID().toString() + extension;

    Path filePath = customerDir.resolve(randomFilename);
    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    KycDetails.DocumentType docType;
    try {
      docType = KycDetails.DocumentType.valueOf(metadata.getFileType().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid document type: " + metadata.getFileType());
    }

    Optional<KycDetails> existing = kycRepository
        .findByCustomerInformationIdAndDocumentTypeAndIsDeletedFalse(metadata.getCustId(), docType);

    KycDetails kyc;
    if (existing.isPresent()) {
      kyc = existing.get();
      kyc.setDocNumber(metadata.getDocNumber());
      kyc.setDocUrl(filePath.toString());
      kyc.setIsVerified(false);
      kyc.setRejectReason(null);
    } else {
      kyc = KycDetails.builder()
          .customerInformation(customer)
          .docNumber(metadata.getDocNumber())
          .docUrl(filePath.toString())
          .documentType(docType)
          .isVerified(false)
          .isDeleted(false)
          .build();
    }

    return kycRepository.save(kyc);
  }

  public Optional<KycDetails> getKycByDocNumber(String docNumber) {
    return kycRepository.findByDocNumberAndIsDeletedFalse(docNumber);
  }

  public List<KycDetails> getCustomerKyc(Long customerId) {
    return kycRepository.findAllByCustomerInformationIdAndIsDeletedFalse(customerId);
  }

  public boolean isDocNumberAlreadyUsed(String docNumber) {
    return kycRepository.existsByDocNumberAndIsDeletedFalse(docNumber);
  }

  public List<KycListResponse> getDocumentsByCustomerId(Long customerId) {
    log.info("Fetching all KYC documents for customer ID: {}", customerId);

    List<KycDetails> documents = kycRepository.findAllByCustomerInformationIdAndIsDeletedFalse(customerId);

    return documents.stream()
        .map(doc -> KycListResponse.builder()
            .id(doc.getId())
            .docNumber(doc.getDocNumber())
            .docUrl(doc.getDocUrl())
            .documentType(doc.getDocumentType())
            .isVerified(doc.getIsVerified())
            .rejectReason(doc.getRejectReason())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .build())
        .toList();
  }

  public List<KycAdminListResponse> getAllKycDocuments() {
    log.info("Fetching all KYC documents (admin view)");

    List<KycDetails> documents = kycRepository.findByIsDeletedFalse();

    return documents.stream()
        .map(doc -> KycAdminListResponse.builder()
            .id(doc.getId())
            .docNumber(doc.getDocNumber())
            .docUrl(doc.getDocUrl())
            .documentType(doc.getDocumentType())
            .isVerified(doc.getIsVerified())
            .rejectReason(doc.getRejectReason())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .customerId(doc.getCustomerInformation().getId())
            .customerName(doc.getCustomerInformation().getName())
            .customerEmail(doc.getCustomerInformation().getEmail())
            .customerPhone(doc.getCustomerInformation().getPhone())
            .build())
        .toList();
  }

  public KycVerifyResponse verifyKycDocument(KycVerifyRequest request) {
    Optional<KycDetails> optionalKyc = kycRepository.findById(request.getKycId());

    if (optionalKyc.isEmpty()) {
      throw new RuntimeException("KYC record not found with ID: " + request.getKycId());
    }

    KycDetails kyc = optionalKyc.get();

    if (request.isVerified()) {
      kyc.setIsVerified(true);
      kyc.setRejectReason(null);
    } else {
      kyc.setIsVerified(false);
      kyc.setRejectReason(request.getRejectReason());
    }

    kyc.setUpdatedAt(LocalDateTime.now());
    kycRepository.save(kyc);

    String message = request.isVerified() ? "KYC document verified successfully" : "KYC document rejected";

    return new KycVerifyResponse(kyc.getId(), kyc.getIsVerified(), kyc.getRejectReason(), message);
  }

  public List<KycStatusResponse> getKycStatusByEmailOrPhone(String email, String phone) {
    Optional<CustomerInformation> customerOpt = Optional.empty();

    if (email != null && !email.isBlank()) {
      customerOpt = customerInformationRepository.findByEmail(email);
    } else if (phone != null && !phone.isBlank()) {
      customerOpt = customerInformationRepository.findByPhone(phone);
    }

    if (customerOpt.isEmpty()) {
      throw new RuntimeException("Customer not found for given email or phone");
    }

    CustomerInformation customer = customerOpt.get();
    List<KycDetails> docs = kycRepository.findByCustomerInformationIdAndIsDeletedFalse(customer.getId());

    return docs.stream()
        .map(doc -> KycStatusResponse.builder()
            .id(doc.getId())
            .docNumber(doc.getDocNumber())
            .docUrl(doc.getDocUrl())
            .documentType(doc.getDocumentType())
            .isVerified(doc.getIsVerified())
            .rejectReason(doc.getRejectReason())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .build())
        .toList();
  }
}
