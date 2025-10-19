package com.capstone.bankingapp.service;

import com.capstone.bankingapp.model.Account;
import com.capstone.bankingapp.model.AccountType;
import com.capstone.bankingapp.model.Customers;
import com.capstone.bankingapp.repository.AccountRepository;
import com.capstone.bankingapp.repository.AccountTypeRepository;
import com.capstone.bankingapp.repository.CustomersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

  private final CustomersRepository customersRepository;
  private final AccountRepository accountRepository;
  private final AccountTypeRepository accountTypeRepository;

  @Transactional
  public Account createDefaultAccountForCustomer(Long customerId, Double initialDeposit) {
    log.info("Creating account for customerId={}, initialDeposit={}", customerId, initialDeposit);

    Customers customer = customersRepository.findByIdAndIsDeletedFalse(customerId)
        .orElseThrow(() -> new RuntimeException("Customer not found: " + customerId));

    String accountTypeCode = customer.getCustomerType() == Customers.CustomerType.INDIVIDUAL
        ? "SAV"
        : "CUR";

    AccountType accountType = accountTypeRepository.findByCode(accountTypeCode)
        .orElseThrow(() -> new RuntimeException("Account Type not found: " + accountTypeCode));

    Account account = Account.builder()
        .accountNumber(System.currentTimeMillis() + "")
        .customer(customer)
        .accountType(accountType)
        .balance(initialDeposit != null ? initialDeposit : 0.0)
        .isActive(true)
        .build();

    Account savedAccount = accountRepository.save(account);

    log.info("Account created successfully: accountNumber={}", savedAccount.getAccountNumber());
    return savedAccount;
  }

}
