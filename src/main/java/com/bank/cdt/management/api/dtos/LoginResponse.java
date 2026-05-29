package com.bank.cdt.management.api.dtos;

import com.bank.cdt.management.api.models.NotificationRecord;

import java.util.List;

public class LoginResponse {

    private Long advisorId;
    private String username;
    private String fullName;
    private List<NotificationRecord> notifications;

    public LoginResponse() {
    }

    public LoginResponse(Long advisorId, String username, String fullName, List<NotificationRecord> notifications) {
        this.advisorId = advisorId;
        this.username = username;
        this.fullName = fullName;
        this.notifications = notifications;
    }

    public Long getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Long advisorId) {
        this.advisorId = advisorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<NotificationRecord> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationRecord> notifications) {
        this.notifications = notifications;
    }
}