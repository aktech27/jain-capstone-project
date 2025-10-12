
package com.capstone.bankingapp.controller;

import com.capstone.bankingapp.dto.request.KycMetadataRequest;
import com.capstone.bankingapp.dto.response.ApiResponse;
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

@RestController
@RequestMapping("/api/v1/auth/kyc")
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
}
