package com.bank.cdt.management.api.dtos;

import com.bank.cdt.management.api.models.NotificationStatus;

public class NotificationRecordRequest {

    private Long advisorId;
    private Long termDepositId;
    private NotificationStatus status;
    private String notes;

    public NotificationRecordRequest() {
    }

    public Long getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Long advisorId) {
        this.advisorId = advisorId;
    }

    public Long getTermDepositId() {
        return termDepositId;
    }

    public void setTermDepositId(Long termDepositId) {
        this.termDepositId = termDepositId;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}