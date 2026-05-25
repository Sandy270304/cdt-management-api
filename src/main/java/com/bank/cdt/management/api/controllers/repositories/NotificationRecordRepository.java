package com.bank.cdt.management.api.repositories;

import com.bank.cdt.management.api.models.NotificationRecord;
import com.bank.cdt.management.api.models.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface NotificationRecordRepository extends JpaRepository<NotificationRecord, Long> {

    List<NotificationRecord> findByAdvisorId(Long advisorId);

    List<NotificationRecord> findByAdvisorIdAndStatus(Long advisorId, NotificationStatus status);

    Optional<NotificationRecord> findByAdvisorIdAndTermDepositId(Long advisorId, Long termDepositId);

    boolean existsByAdvisorIdAndTermDepositIdAndStatusIn(
            Long advisorId,
            Long termDepositId,
            Collection<NotificationStatus> statuses
    );
}