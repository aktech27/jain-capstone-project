package com.capstone.bankingapp.service;

import com.capstone.bankingapp.dto.request.KycMetadataRequest;
import com.capstone.bankingapp.model.CustomerInformation;
import com.capstone.bankingapp.model.KycDetails;
import com.capstone.bankingapp.repository.CustomerInformationRepository;
import com.capstone.bankingapp.repository.KycRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
}
