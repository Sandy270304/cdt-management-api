package com.bank.cdt.management.api.services;

import com.bank.cdt.management.api.dtos.TermDepositRequest;
import com.bank.cdt.management.api.models.Customer;
import com.bank.cdt.management.api.models.TermDeposit;
import com.bank.cdt.management.api.models.TermDepositStatus;
import com.bank.cdt.management.api.repositories.CustomerRepository;
import com.bank.cdt.management.api.repositories.TermDepositRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TermDepositService {

    private final TermDepositRepository termDepositRepository;
    private final CustomerRepository customerRepository;

    public TermDepositService(
            TermDepositRepository termDepositRepository,
            CustomerRepository customerRepository
    ) {
        this.termDepositRepository = termDepositRepository;
        this.customerRepository = customerRepository;
    }

    public List<TermDeposit> findAllTermDeposits() {
        return termDepositRepository.findAll();
    }

    public TermDeposit findTermDepositById(Long id) {
        return termDepositRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Term deposit not found with id: " + id));
    }

    public List<TermDeposit> findTermDepositsByCustomerId(Long customerId) {
        return termDepositRepository.findByCustomerId(customerId);
    }

    public List<TermDeposit> findTermDepositsMaturingInNextFiveDays() {
        LocalDate today = LocalDate.now();
        LocalDate limitDate = today.plusDays(5);

        return termDepositRepository.findByStatusAndMaturityDateBetween(
                TermDepositStatus.ACTIVE,
                today,
                limitDate
        );
    }

    public TermDeposit createTermDeposit(TermDepositRequest request) {
        if (termDepositRepository.existsByCertificateNumber(request.getCertificateNumber())) {
            throw new RuntimeException("A term deposit already exists with certificate number: " + request.getCertificateNumber());
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId()));

        TermDeposit termDeposit = new TermDeposit();
        termDeposit.setCustomer(customer);
        termDeposit.setCertificateNumber(request.getCertificateNumber());
        termDeposit.setPrincipalAmount(request.getPrincipalAmount());
        termDeposit.setAnnualInterestRate(request.getAnnualInterestRate());
        termDeposit.setOpeningDate(request.getOpeningDate());
        termDeposit.setMaturityDate(request.getMaturityDate());
        termDeposit.setTermDays(request.getTermDays());

        if (request.getStatus() != null) {
            termDeposit.setStatus(request.getStatus());
        } else {
            termDeposit.setStatus(TermDepositStatus.ACTIVE);
        }

        return termDepositRepository.save(termDeposit);
    }

    public TermDeposit updateTermDeposit(Long id, TermDepositRequest request) {
        TermDeposit existingTermDeposit = findTermDepositById(id);

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId()));

        existingTermDeposit.setCustomer(customer);
        existingTermDeposit.setCertificateNumber(request.getCertificateNumber());
        existingTermDeposit.setPrincipalAmount(request.getPrincipalAmount());
        existingTermDeposit.setAnnualInterestRate(request.getAnnualInterestRate());
        existingTermDeposit.setOpeningDate(request.getOpeningDate());
        existingTermDeposit.setMaturityDate(request.getMaturityDate());
        existingTermDeposit.setTermDays(request.getTermDays());

        if (request.getStatus() != null) {
            existingTermDeposit.setStatus(request.getStatus());
        } else {
            existingTermDeposit.setStatus(TermDepositStatus.ACTIVE);
        }

        return termDepositRepository.save(existingTermDeposit);
    }

    public void deleteTermDeposit(Long id) {
        TermDeposit existingTermDeposit = findTermDepositById(id);
        termDepositRepository.delete(existingTermDeposit);
    }
}
