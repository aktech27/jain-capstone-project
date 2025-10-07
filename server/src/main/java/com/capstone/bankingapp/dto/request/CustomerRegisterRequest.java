package com.capstone.bankingapp.dto.request;

import java.time.LocalDate;

import com.capstone.bankingapp.model.CustomerInformation.Gender;

import lombok.Data;

@Data
public class CustomerRegisterRequest {

  private BasicInfo basicInfo;
  private AddressDetails addressDetails;
  private NomineeDetails nomineeDetails;

  @Data
  public static class BasicInfo {
    private String name;
    private String email;
    private String phone;
    private LocalDate dob;
    private Gender gender;
  }

  @Data
  public static class AddressDetails {
    private Address current;
    private Address permanant;

    @Data
    public static class Address {
      private String line1;
      private String line2;
      private String line3;
      private String city;
      private String state;
      private String pincode;
    }
  }

  @Data
  public static class NomineeDetails {
    private String name;
    private String phone;
    private String relation;
  }
}