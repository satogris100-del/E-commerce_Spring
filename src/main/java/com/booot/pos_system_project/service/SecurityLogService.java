package com.booot.pos_system_project.service;

import com.booot.pos_system_project.dto.response.auth.SecurityLogResponse;
import com.booot.pos_system_project.model.Security;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SecurityLogService  {
    void record(Security event);
    Page<SecurityLogResponse> getRecentLogs(Pageable pageable);
}
