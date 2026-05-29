package com.bank.cdt.management.api.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "term_deposits")
public class TermDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "certificate_number", nullable = false, unique = true, length = 50)
    private String certificateNumber;

    @Column(name = "principal_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal principalAmount;

    @Column(name = "annual_interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal annualInterestRate;

    @Column(name = "opening_date", nullable = false)
    private LocalDate openingDate;

    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate;

    @Column(name = "term_days", nullable = false)
    private Integer termDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TermDepositStatus status = TermDepositStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public TermDeposit() {
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = TermDepositStatus.ACTIVE;
        }
    }

    public Long getId() {
        return id;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(BigDecimal annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Integer getTermDays() {
        return termDays;
    }

    public void setTermDays(Integer termDays) {
        this.termDays = termDays;
    }

    public TermDepositStatus getStatus() {
        return status;
    }

    public void setStatus(TermDepositStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}