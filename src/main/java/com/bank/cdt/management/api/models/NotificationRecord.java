package com.bank.cdt.management.api.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "notification_records",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_notification_advisor_term_deposit",
                        columnNames = {"advisor_id", "term_deposit_id"}
                )
        }
)
public class NotificationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advisor_id", nullable = false)
    private Advisor advisor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "term_deposit_id", nullable = false)
    private TermDeposit termDeposit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private NotificationStatus status = NotificationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 30)
    private NotificationPriority priority;

    @Column(name = "days_remaining", nullable = false)
    private Integer daysRemaining;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "seen_at")
    private LocalDateTime seenAt;

    @Column(name = "managed_at")
    private LocalDateTime managedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public NotificationRecord() {
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = NotificationStatus.PENDING;
        }
    }

    public Long getId() {
        return id;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public TermDeposit getTermDeposit() {
        return termDeposit;
    }

    public void setTermDeposit(TermDeposit termDeposit) {
        this.termDeposit = termDeposit;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public NotificationPriority getPriority() {
        return priority;
    }

    public void setPriority(NotificationPriority priority) {
        this.priority = priority;
    }

    public Integer getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(Integer daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getSeenAt() {
        return seenAt;
    }

    public void setSeenAt(LocalDateTime seenAt) {
        this.seenAt = seenAt;
    }

    public LocalDateTime getManagedAt() {
        return managedAt;
    }

    public void setManagedAt(LocalDateTime managedAt) {
        this.managedAt = managedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}