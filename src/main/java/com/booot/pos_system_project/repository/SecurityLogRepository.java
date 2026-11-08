package com.booot.pos_system_project.repository;

import com.booot.pos_system_project.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityLogRepository extends JpaRepository<Security, Long> {
}
