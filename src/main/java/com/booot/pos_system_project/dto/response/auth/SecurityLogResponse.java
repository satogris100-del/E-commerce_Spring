package com.booot.pos_system_project.dto.response.auth;

import java.time.LocalDateTime;

public record SecurityLogResponse(
        Long id,
        String eventType,
        Long actorId,
        String actorUsername,
        String actorRole,
        String httpMethod,
        String requestPath,
        int responseStatus,
        String clientIp,
        String authenticationType,
        LocalDateTime createdAt
) {
}