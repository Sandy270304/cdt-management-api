package com.bank.cdt.management.api.controllers;

import com.bank.cdt.management.api.dtos.TermDepositRequest;
import com.bank.cdt.management.api.models.TermDeposit;
import com.bank.cdt.management.api.services.TermDepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/term-deposits")
public class TermDepositController {

    private final TermDepositService termDepositService;

    public TermDepositController(TermDepositService termDepositService) {
        this.termDepositService = termDepositService;
    }

    @GetMapping
    public ResponseEntity<List<TermDeposit>> findAllTermDeposits() {
        return ResponseEntity.ok(termDepositService.findAllTermDeposits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermDeposit> findTermDepositById(@PathVariable Long id) {
        return ResponseEntity.ok(termDepositService.findTermDepositById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TermDeposit>> findTermDepositsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(termDepositService.findTermDepositsByCustomerId(customerId));
    }

    @GetMapping("/maturing/next-five-days")
    public ResponseEntity<List<TermDeposit>> findTermDepositsMaturingInNextFiveDays() {
        return ResponseEntity.ok(termDepositService.findTermDepositsMaturingInNextFiveDays());
    }

    @PostMapping
    public ResponseEntity<TermDeposit> createTermDeposit(@RequestBody TermDepositRequest request) {
        return ResponseEntity.ok(termDepositService.createTermDeposit(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TermDeposit> updateTermDeposit(
            @PathVariable Long id,
            @RequestBody TermDepositRequest request
    ) {
        return ResponseEntity.ok(termDepositService.updateTermDeposit(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermDeposit(@PathVariable Long id) {
        termDepositService.deleteTermDeposit(id);
        return ResponseEntity.noContent().build();
    }
}