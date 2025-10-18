package com.capstone.bankingapp.config;

import com.capstone.bankingapp.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getRequestURI();
    log.debug("Incoming request path: {}", path);

    if (path.startsWith("/api/v1/auth/") || path.equals("/health-check") || path.equals("/api/v1/kyc/upload-doc")
        || path.equals("/api/v1/kyc/doc-status")) {
      log.debug("Skipping authentication for public endpoint: {}", path);
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader("Authorization");
    log.debug("Authorization header: {}", authHeader);

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      log.debug("Extracted token: {}", token);

      try {
        if (jwtUtil.validateToken(token)) {
          String username = jwtUtil.extractUsername(token);
          log.info("JWT validated successfully for user: {}", username);

          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, null);
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authToken);
          log.debug("Authentication set for user: {}", username);
        } else {
          log.warn("Invalid JWT token for path: {}", path);
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
          return;
        }
      } catch (Exception e) {
        log.error("Error validating JWT token: {}", e.getMessage(), e);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT validation error");
        return;
      }

    } else if (requiresAuthentication(request)) {
      log.warn("Missing JWT token for protected endpoint: {}", path);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT token");
      return;
    } else {
      log.debug("Request does not require authentication: {}", path);
    }

    filterChain.doFilter(request, response);
  }

  private boolean requiresAuthentication(HttpServletRequest request) {
    String path = request.getRequestURI();
    boolean required = !path.equals("/api/v1/kyc/upload-doc")
        && !path.startsWith("/api/v1/auth/login")
        && !path.startsWith("/api/v1/auth/register")
        && !path.startsWith("/health-check");
    log.trace("Requires authentication for path {}: {}", path, required);
    return required;
  }
}
