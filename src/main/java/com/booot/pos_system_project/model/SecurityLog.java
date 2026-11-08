package com.booot.pos_system_project.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_log_tbl", indexes = {
        @Index(name = "idx_security_log_created_at", columnList = "created_at"),
        @Index(name = "idx_security_log_actor_id", columnList = "actor_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false, length = 64)
    private String eventType;

    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "actor_username", length = 120)
    private String actorUsername;

    @Column(name = "actor_role", length = 80)
    private String actorRole;

    @Column(name = "http_method", nullable = false, length = 10)
    private String httpMethod;

    @Column(name = "request_path", nullable = false, length = 300)
    private String requestPath;

    @Column(name = "response_status", nullable = false)
    private int responseStatus;

    @Column(name = "client_ip", length = 64)
    private String clientIp;

    @Column(name = "authentication_type", nullable = false, length = 32)
    private String authenticationType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


}
