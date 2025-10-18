package com.capstone.bankingapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.bankingapp.model.KycDetails;
import com.capstone.bankingapp.model.KycDetails.DocumentType;

@Repository
public interface KycRepository extends JpaRepository<KycDetails, Long> {
  Optional<KycDetails> findByDocNumberAndIsDeletedFalse(String docNumber);

  List<KycDetails> findAllByCustomerInformationIdAndIsDeletedFalse(Long customerInfoId);

  Optional<KycDetails> findByCustomerInformationIdAndDocumentTypeAndIsDeletedFalse(Long customerInfoId,
      DocumentType documentType);

  boolean existsByDocNumberAndIsDeletedFalse(String docNumber);

  List<KycDetails> findAllByIsVerifiedFalseAndIsDeletedFalse();

  List<KycDetails> findAllByIsDeletedTrue();

  List<KycDetails> findByIsDeletedFalse();

  List<KycDetails> findByCustomerInformationIdAndIsDeletedFalse(Long customerInfoId);

}
