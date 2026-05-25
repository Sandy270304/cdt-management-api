package com.bank.cdt.management.api.repositories;

import com.bank.cdt.management.api.models.TermDeposit;
import com.bank.cdt.management.api.models.TermDepositStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TermDepositRepository extends JpaRepository<TermDeposit, Long> {

    boolean existsByCertificateNumber(String certificateNumber);

    List<TermDeposit> findByCustomerId(Long customerId);

    List<TermDeposit> findByStatusAndMaturityDateBetween(
            TermDepositStatus status,
            LocalDate startDate,
            LocalDate endDate
    );
}