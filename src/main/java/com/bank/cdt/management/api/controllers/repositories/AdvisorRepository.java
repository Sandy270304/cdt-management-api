package com.bank.cdt.management.api.repositories;

import com.bank.cdt.management.api.models.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {

    Optional<Advisor> findByUsername(String username);

    boolean existsByUsername(String username);
}