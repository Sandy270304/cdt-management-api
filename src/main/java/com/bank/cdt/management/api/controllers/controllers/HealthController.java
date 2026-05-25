package com.bank.cdt.management.api.controllers.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/v1/health")
    public String healthCheck() {
        return "CDT Management API is running";
    }
}