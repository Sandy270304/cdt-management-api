package com.bank.cdt.management.api.dtos;

import com.bank.cdt.management.api.models.TermDepositStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TermDepositRequest {

    private Long customerId;
    private String certificateNumber;
    private BigDecimal principalAmount;
    private BigDecimal annualInterestRate;
    private LocalDate openingDate;
    private LocalDate maturityDate;
    private Integer termDays;
    private TermDepositStatus status;

    public TermDepositRequest() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
}