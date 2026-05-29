package com.bank.cdt.management.api.services;

import com.bank.cdt.management.api.dtos.AdvisorRequest;
import com.bank.cdt.management.api.models.Advisor;
import com.bank.cdt.management.api.repositories.AdvisorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvisorService {

    private final AdvisorRepository advisorRepository;

    public AdvisorService(AdvisorRepository advisorRepository) {
        this.advisorRepository = advisorRepository;
    }

    public List<Advisor> findAllAdvisors() {
        return advisorRepository.findAll();
    }

    public Advisor findAdvisorById(Long id) {
        return advisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advisor not found with id: " + id));
    }

    public Advisor findAdvisorByUsername(String username) {
        return advisorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Advisor not found with username: " + username));
    }

    public Advisor createAdvisor(AdvisorRequest request) {
        if (advisorRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("An advisor already exists with username: " + request.getUsername());
        }

        Advisor advisor = new Advisor();
        advisor.setUsername(request.getUsername());
        advisor.setPasswordHash(request.getPasswordHash());
        advisor.setFullName(request.getFullName());
        advisor.setEmail(request.getEmail());
        advisor.setPhoneNumber(request.getPhoneNumber());

        if (request.getActive() != null) {
            advisor.setActive(request.getActive());
        } else {
            advisor.setActive(true);
        }

        return advisorRepository.save(advisor);
    }

    public Advisor updateAdvisor(Long id, AdvisorRequest request) {
        Advisor existingAdvisor = findAdvisorById(id);

        existingAdvisor.setUsername(request.getUsername());
        existingAdvisor.setPasswordHash(request.getPasswordHash());
        existingAdvisor.setFullName(request.getFullName());
        existingAdvisor.setEmail(request.getEmail());
        existingAdvisor.setPhoneNumber(request.getPhoneNumber());

        if (request.getActive() != null) {
            existingAdvisor.setActive(request.getActive());
        }

        return advisorRepository.save(existingAdvisor);
    }

    public void deleteAdvisor(Long id) {
        Advisor existingAdvisor = findAdvisorById(id);
        advisorRepository.delete(existingAdvisor);
    }
}