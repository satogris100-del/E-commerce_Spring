package com.booot.pos_system_project.service.implement.middleware;

import com.booot.pos_system_project.dto.response.auth.SecurityLogResponse;
import com.booot.pos_system_project.model.Security;
import com.booot.pos_system_project.repository.SecurityLogRepository;
import com.booot.pos_system_project.service.SecurityLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SecurityLoServiceImplement implements SecurityLogService {
    private final SecurityLogRepository securityLogRepository;
    public SecurityLoServiceImplement(SecurityLogRepository securityLogRepository) {
        this.securityLogRepository = securityLogRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void record(Security event) {
        securityLogRepository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SecurityLogResponse> getRecentLogs(Pageable pageable) {
        return securityLogRepository.findAll(pageable).map(this::toResponse);
    }

    private SecurityLogResponse toResponse(Security e) {
        return new SecurityLogResponse(
                e.getId(),
                e.getEventType(),
                e.getActorId(),
                e.getActorUsername(),
                e.getActorRole(),
                e.getHttpMethod(),
                e.getRequestPath(),
                e.getResponseStatus(),
                e.getClientIp(),
                e.getAuthenticationType(),
                e.getCreatedAt()
        );
    }
}
