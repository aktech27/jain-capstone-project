package com.capstone.bankingapp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@RestController
public class HealthController {
  @GetMapping("/health-check")
  public Map<String, Object> healthCheck() {
    RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
    long uptimeMillis = runtime.getUptime();
    return Map.of(
        "status", "Server is healthy",
        "uptime", uptimeMillis,
        "pid", runtime.getPid(),
        "jvmVersion", runtime.getVmVersion(),
        "jvmVendor", runtime.getVmVendor());
  }
}
