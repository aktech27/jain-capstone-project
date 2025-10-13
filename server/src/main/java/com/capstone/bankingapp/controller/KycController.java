
package com.capstone.bankingapp.controller;

import com.capstone.bankingapp.dto.request.KycMetadataRequest;
import com.capstone.bankingapp.dto.request.KycViewRequest;
import com.capstone.bankingapp.dto.response.ApiResponse;
import com.capstone.bankingapp.dto.response.KycAdminListResponse;
import com.capstone.bankingapp.dto.response.KycListResponse;
import com.capstone.bankingapp.dto.response.KycUploadResponse;
import com.capstone.bankingapp.model.KycDetails;
import com.capstone.bankingapp.service.KycService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kyc")
@RequiredArgsConstructor
@Slf4j
public class KycController {

  private final Path uploadDir = Paths.get("uploads/").toAbsolutePath().normalize();
  private final KycService kycService;

  @PostConstruct
  public void init() throws IOException {
    Files.createDirectories(uploadDir);
  }

  @PostMapping(value = "/upload-doc", consumes = "multipart/form-data")
  public ResponseEntity<ApiResponse<KycUploadResponse>> uploadDocument(
      @RequestPart("metadata") KycMetadataRequest metadata,
      @RequestPart("file") MultipartFile file) {

    try {
      log.info("Uploading file for customer ID {} of type {}", metadata.getCustId(), metadata.getFileType());
      KycDetails kycDetails = kycService.saveKycDocument(metadata, file);
      log.info("Saved file details {}", kycDetails);

      KycUploadResponse response = new KycUploadResponse(
          kycDetails.getId(),
          kycDetails.getDocUrl(),
          metadata.getFileType(),
          metadata.getCustId());

      return ResponseEntity.ok(
          new ApiResponse<>(true, "File uploaded successfully", response));
    } catch (IOException e) {
      log.error("Error uploading file", e);
      return ResponseEntity.status(500).body(
          new ApiResponse<>(false, "File upload failed: " + e.getMessage(), null));
    }
  }

  @GetMapping("/list/{customerId}")
  public ResponseEntity<ApiResponse<List<KycListResponse>>> getCustomerDocuments(@PathVariable Long customerId) {
    log.info("Fetching KYC documents for customer ID: {}", customerId);

    List<KycListResponse> documents = kycService.getDocumentsByCustomerId(customerId);

    return ResponseEntity.ok(
        new ApiResponse<>(true, "KYC documents fetched successfully", documents));
  }

  @GetMapping("/list/all")
  public ResponseEntity<ApiResponse<List<KycAdminListResponse>>> getAllKycDocuments() {
    log.info("Fetching all KYC documents (admin view)");

    List<KycAdminListResponse> documents = kycService.getAllKycDocuments();

    return ResponseEntity.ok(
        new ApiResponse<>(true, "All KYC documents fetched successfully", documents));
  }

  @PostMapping("/view")
  public ResponseEntity<?> viewDocument(@RequestBody KycViewRequest request) {
    try {
      log.info("Serving KYC document for customer {} and file {}", request.getCustId(), request.getFileName());

      // Build secure path: uploads/cust_{custId}/{fileName}
      Path baseDir = Paths.get("uploads").toAbsolutePath().normalize();
      Path customerDir = baseDir.resolve("cust_" + request.getCustId()).normalize();
      Path filePath = customerDir.resolve(request.getFileName()).normalize();

      // Security check — ensure inside upload dir
      if (!filePath.startsWith(baseDir)) {
        log.warn("Access attempt outside uploads directory: {}", filePath);
        return ResponseEntity.status(403).body("Access denied");
      }

      if (!Files.exists(filePath)) {
        log.warn("File not found: {}", filePath);
        return ResponseEntity.notFound().build();
      }

      String contentType = Files.probeContentType(filePath);
      if (contentType == null) {
        contentType = "application/octet-stream";
      }

      byte[] fileBytes = Files.readAllBytes(filePath);

      return ResponseEntity.ok()
          .header("Content-Type", contentType)
          .header("Content-Disposition", "inline; filename=\"" + filePath.getFileName().toString() + "\"")
          .body(fileBytes);

    } catch (IOException e) {
      log.error("Error serving file for customer {}: {}", request.getCustId(), e.getMessage());
      return ResponseEntity.status(500)
          .body("Error reading file: " + e.getMessage());
    }
  }
}
