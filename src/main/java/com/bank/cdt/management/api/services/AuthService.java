package com.bank.cdt.management.api.services;

import com.bank.cdt.management.api.dtos.LoginRequest;
import com.bank.cdt.management.api.dtos.LoginResponse;
import com.bank.cdt.management.api.models.Advisor;
import com.bank.cdt.management.api.models.NotificationRecord;
import com.bank.cdt.management.api.repositories.AdvisorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final AdvisorRepository advisorRepository;
    private final NotificationRecordService notificationRecordService;

    public AuthService(
            AdvisorRepository advisorRepository,
            NotificationRecordService notificationRecordService
    ) {
        this.advisorRepository = advisorRepository;
        this.notificationRecordService = notificationRecordService;
    }

    public LoginResponse login(LoginRequest request) {
        Advisor advisor = advisorRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password."));

        if (!Boolean.TRUE.equals(advisor.getActive())) {
            throw new RuntimeException("Advisor account is inactive.");
        }

        if (!advisor.getPasswordHash().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }

        List<NotificationRecord> notifications =
                notificationRecordService.generateNotificationsForAdvisor(advisor.getId());

        return new LoginResponse(
                advisor.getId(),
                advisor.getUsername(),
                advisor.getFullName(),
                notifications
        );
    }
}
