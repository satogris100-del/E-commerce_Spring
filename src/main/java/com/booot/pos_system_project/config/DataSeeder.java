package com.booot.pos_system_project.config;

import com.booot.pos_system_project.enums.role.RoleUser;
import com.booot.pos_system_project.model.Role;
import com.booot.pos_system_project.model.User;
import com.booot.pos_system_project.repository.RoleRepository;
import com.booot.pos_system_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
public class DataSeeder {
    @Bean
    public CommandLineRunner seedData(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            PlatformTransactionManager txManager,
            @Value("${app.admin.username}") String adminUsername,
            @Value("${app.admin.email}") String adminEmail,
            @Value("${app.admin.password}") String adminPassword
    ) {
        return args -> new TransactionTemplate(txManager).executeWithoutResult(status -> {
            for (RoleUser role: RoleUser.values()) {
                roleRepository.findByName(role)
                        .orElseGet(()-> roleRepository.save(new Role(role)));
            }
            Role adminRole = roleRepository.findByName(RoleUser.ADMIN).orElseThrow();
            User admin = userRepository.findByUsername(adminUsername).orElseGet(User::new);
            boolean isNew = admin.getId() == null;
            admin.setUsername(adminUsername.trim());
            admin.setEmail(adminEmail.trim());
            admin.setPassword(passwordEncoder.encode(adminPassword.trim()));
            admin.setEnabled(true);
            admin.setLockTime(null);

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);
            userRepository.save(admin);

            log.info("Admin '{}' {}", adminUsername, isNew ? "created" : "repaired");
        });
    }
}
