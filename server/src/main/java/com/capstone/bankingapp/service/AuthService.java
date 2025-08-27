package com.capstone.bankingapp.service;

import com.capstone.bankingapp.dto.request.AdminLoginRequest;
import com.capstone.bankingapp.dto.response.AdminLoginResponse;
import com.capstone.bankingapp.model.AdminUser;
import com.capstone.bankingapp.repository.AdminUserRepository;
import com.capstone.bankingapp.util.JwtUtil;
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
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

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
}
