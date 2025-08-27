package com.capstone.bankingapp.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

  private final SecretKey secretKey;

  @Value("${JWT_EXPIRATION}")
  private long jwtExpirationMs;

  public JwtUtil(@Value("${JWT_SECRET}") String jwtSecret) {
    this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String generateToken(Map<String, Object> payload) {
    Map<String, Object> claims = new HashMap<>(payload);
    return Jwts.builder()
        .setSubject((String) payload.get("username"))
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }
}
