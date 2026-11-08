package com.booot.pos_system_project.service.middleware;

import com.booot.pos_system_project.dto.response.auth.SecurityLogResponse;
import com.booot.pos_system_project.model.Security;
import com.booot.pos_system_project.repository.SecurityLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SecurityLoService {
    private final SecurityLogRepository securityRepository;
    public SecurityLoService(SecurityLogRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void record(Security eve) {
        securityRepository.save(eve);
    }
    @Transactional(readOnly = true)
    public Page<SecurityLogResponse> getRecent(Pageable pageables) {
        return securityRepository.findAll(pageables).map(
                eve ->new SecurityLogResponse(
                        eve.getId(),
                        eve.getEventType(),
                        eve.getActorId(),
                        eve.getActorUsername(),
                        eve.getActorRole(),
                        eve.getHttpMethod(),
                        eve.getRequestPath(),
                        eve.getResponseStatus(),
                        eve.getClientIp(),
                        eve.getAuthenticationType(),
                        eve.getCreatedAt()
                )
        );
    }

}
