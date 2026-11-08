package com.booot.pos_system_project.repository;

import com.booot.pos_system_project.enums.role.RoleUser;
import com.booot.pos_system_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findDistinctByRoles_Name(RoleUser role);
}
