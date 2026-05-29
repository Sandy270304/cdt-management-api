package com.bank.cdt.management.api.controllers;

import com.bank.cdt.management.api.dtos.NotificationRecordRequest;
import com.bank.cdt.management.api.models.NotificationRecord;
import com.bank.cdt.management.api.services.NotificationRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationRecordController {

    private final NotificationRecordService notificationRecordService;

    public NotificationRecordController(NotificationRecordService notificationRecordService) {
        this.notificationRecordService = notificationRecordService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationRecord>> findAllNotifications() {
        return ResponseEntity.ok(notificationRecordService.findAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationRecord> findNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationRecordService.findNotificationById(id));
    }

    @GetMapping("/advisor/{advisorId}")
    public ResponseEntity<List<NotificationRecord>> findNotificationsByAdvisorId(@PathVariable Long advisorId) {
        return ResponseEntity.ok(notificationRecordService.findNotificationsByAdvisorId(advisorId));
    }

    @GetMapping("/advisor/{advisorId}/pending")
    public ResponseEntity<List<NotificationRecord>> findPendingNotificationsByAdvisorId(@PathVariable Long advisorId) {
        return ResponseEntity.ok(notificationRecordService.findPendingNotificationsByAdvisorId(advisorId));
    }

    @PostMapping("/generate/advisor/{advisorId}")
    public ResponseEntity<List<NotificationRecord>> generateNotificationsForAdvisor(@PathVariable Long advisorId) {
        return ResponseEntity.ok(notificationRecordService.generateNotificationsForAdvisor(advisorId));
    }

    @PostMapping
    public ResponseEntity<NotificationRecord> createNotification(@RequestBody NotificationRecordRequest request) {
        return ResponseEntity.ok(notificationRecordService.createNotification(request));
    }

    @PutMapping("/{id}/manage")
    public ResponseEntity<NotificationRecord> manageNotification(
            @PathVariable Long id,
            @RequestBody NotificationRecordRequest request
    ) {
        return ResponseEntity.ok(notificationRecordService.manageNotification(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationRecordService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}