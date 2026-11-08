package com.booot.pos_system_project.repository;

import com.booot.pos_system_project.enums.role.RoleUser;
import com.booot.pos_system_project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleUser name);
}
