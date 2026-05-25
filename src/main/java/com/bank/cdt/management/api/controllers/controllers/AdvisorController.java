package com.bank.cdt.management.api.controllers;

import com.bank.cdt.management.api.dtos.AdvisorRequest;
import com.bank.cdt.management.api.models.Advisor;
import com.bank.cdt.management.api.services.AdvisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/advisors")
public class AdvisorController {

    private final AdvisorService advisorService;

    public AdvisorController(AdvisorService advisorService) {
        this.advisorService = advisorService;
    }

    @GetMapping
    public ResponseEntity<List<Advisor>> findAllAdvisors() {
        return ResponseEntity.ok(advisorService.findAllAdvisors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advisor> findAdvisorById(@PathVariable Long id) {
        return ResponseEntity.ok(advisorService.findAdvisorById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Advisor> findAdvisorByUsername(@PathVariable String username) {
        return ResponseEntity.ok(advisorService.findAdvisorByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Advisor> createAdvisor(@RequestBody AdvisorRequest request) {
        return ResponseEntity.ok(advisorService.createAdvisor(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Advisor> updateAdvisor(
            @PathVariable Long id,
            @RequestBody AdvisorRequest request
    ) {
        return ResponseEntity.ok(advisorService.updateAdvisor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvisor(@PathVariable Long id) {
        advisorService.deleteAdvisor(id);
        return ResponseEntity.noContent().build();
    }
}