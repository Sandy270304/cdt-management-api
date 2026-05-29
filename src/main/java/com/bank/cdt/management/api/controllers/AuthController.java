package com.bank.cdt.management.api.controllers;

import com.bank.cdt.management.api.dtos.LoginRequest;
import com.bank.cdt.management.api.dtos.LoginResponse;
import com.bank.cdt.management.api.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}