package com.bank.cdt.management.api.services;

import com.bank.cdt.management.api.dtos.NotificationRecordRequest;
import com.bank.cdt.management.api.models.*;
import com.bank.cdt.management.api.repositories.AdvisorRepository;
import com.bank.cdt.management.api.repositories.NotificationRecordRepository;
import com.bank.cdt.management.api.repositories.TermDepositRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationRecordService {

    private final NotificationRecordRepository notificationRecordRepository;
    private final AdvisorRepository advisorRepository;
    private final TermDepositRepository termDepositRepository;

    public NotificationRecordService(
            NotificationRecordRepository notificationRecordRepository,
            AdvisorRepository advisorRepository,
            TermDepositRepository termDepositRepository
    ) {
        this.notificationRecordRepository = notificationRecordRepository;
        this.advisorRepository = advisorRepository;
        this.termDepositRepository = termDepositRepository;
    }

    public List<NotificationRecord> findAllNotifications() {
        return notificationRecordRepository.findAll();
    }

    public NotificationRecord findNotificationById(Long id) {
        return notificationRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification record not found with id: " + id));
    }

    public List<NotificationRecord> findNotificationsByAdvisorId(Long advisorId) {
        return notificationRecordRepository.findByAdvisorId(advisorId);
    }

    public List<NotificationRecord> findPendingNotificationsByAdvisorId(Long advisorId) {
        return notificationRecordRepository.findByAdvisorIdAndStatus(advisorId, NotificationStatus.PENDING);
    }

    public List<NotificationRecord> generateNotificationsForAdvisor(Long advisorId) {
        Advisor advisor = advisorRepository.findById(advisorId)
                .orElseThrow(() -> new RuntimeException("Advisor not found with id: " + advisorId));

        LocalDate today = LocalDate.now();
        LocalDate limitDate = today.plusDays(5);

        List<TermDeposit> termDeposits = termDepositRepository.findByStatusAndMaturityDateBetween(
                TermDepositStatus.ACTIVE,
                today,
                limitDate
        );

        List<NotificationRecord> generatedNotifications = new ArrayList<>();

        for (TermDeposit termDeposit : termDeposits) {
            NotificationRecord notificationRecord = notificationRecordRepository
                    .findByAdvisorIdAndTermDepositId(advisor.getId(), termDeposit.getId())
                    .orElse(null);

            if (notificationRecord != null &&
                    (notificationRecord.getStatus() == NotificationStatus.CONTACTED ||
                            notificationRecord.getStatus() == NotificationStatus.RENEWED)) {
                continue;
            }

            int daysRemaining = calculateDaysRemaining(termDeposit.getMaturityDate());
            NotificationPriority priority = calculatePriority(daysRemaining);

            if (notificationRecord == null) {
                notificationRecord = new NotificationRecord();
                notificationRecord.setAdvisor(advisor);
                notificationRecord.setTermDeposit(termDeposit);
                notificationRecord.setStatus(NotificationStatus.PENDING);
            }

            notificationRecord.setDaysRemaining(daysRemaining);
            notificationRecord.setPriority(priority);
            notificationRecord.setSeenAt(LocalDateTime.now());

            generatedNotifications.add(notificationRecordRepository.save(notificationRecord));
        }

        return generatedNotifications;
    }

    public NotificationRecord createNotification(NotificationRecordRequest request) {
        Advisor advisor = advisorRepository.findById(request.getAdvisorId())
                .orElseThrow(() -> new RuntimeException("Advisor not found with id: " + request.getAdvisorId()));

        TermDeposit termDeposit = termDepositRepository.findById(request.getTermDepositId())
                .orElseThrow(() -> new RuntimeException("Term deposit not found with id: " + request.getTermDepositId()));

        boolean alreadyManaged = notificationRecordRepository.existsByAdvisorIdAndTermDepositIdAndStatusIn(
                advisor.getId(),
                termDeposit.getId(),
                List.of(NotificationStatus.CONTACTED, NotificationStatus.RENEWED)
        );

        if (alreadyManaged) {
            throw new RuntimeException("This notification has already been managed for this advisor and term deposit.");
        }

        NotificationRecord notificationRecord = new NotificationRecord();
        notificationRecord.setAdvisor(advisor);
        notificationRecord.setTermDeposit(termDeposit);
        notificationRecord.setStatus(
                request.getStatus() != null ? request.getStatus() : NotificationStatus.PENDING
        );
        notificationRecord.setNotes(request.getNotes());
        notificationRecord.setDaysRemaining(calculateDaysRemaining(termDeposit.getMaturityDate()));
        notificationRecord.setPriority(calculatePriority(notificationRecord.getDaysRemaining()));
        notificationRecord.setSeenAt(LocalDateTime.now());

        return notificationRecordRepository.save(notificationRecord);
    }

    public NotificationRecord manageNotification(Long id, NotificationRecordRequest request) {
        NotificationRecord existingNotification = findNotificationById(id);

        if (request.getStatus() != null) {
            existingNotification.setStatus(request.getStatus());
        }

        if (request.getNotes() != null) {
            existingNotification.setNotes(request.getNotes());
        }

        existingNotification.setManagedAt(LocalDateTime.now());

        if (existingNotification.getStatus() == NotificationStatus.RENEWED) {
            TermDeposit termDeposit = existingNotification.getTermDeposit();
            termDeposit.setStatus(TermDepositStatus.RENEWED);
            termDepositRepository.save(termDeposit);
        }

        return notificationRecordRepository.save(existingNotification);
    }

    public void deleteNotification(Long id) {
        NotificationRecord existingNotification = findNotificationById(id);
        notificationRecordRepository.delete(existingNotification);
    }

    private int calculateDaysRemaining(LocalDate maturityDate) {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), maturityDate);
    }

    private NotificationPriority calculatePriority(int daysRemaining) {
        if (daysRemaining <= 2) {
            return NotificationPriority.CRITICAL;
        }

        if (daysRemaining <= 4) {
            return NotificationPriority.HIGH;
        }

        return NotificationPriority.MEDIUM;
    }
}