package com.booot.pos_system_project.controller.admin;

import com.booot.pos_system_project.dto.response.ApiResponse;
import com.booot.pos_system_project.dto.response.auth.SecurityLogResponse;
import com.booot.pos_system_project.service.SecurityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/security-logs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SecurityController {
    private final SecurityLogService securityLogService;
    @GetMapping
    public ResponseEntity<ApiResponse<Page<SecurityLogResponse>>> getRecentLog(
            @PageableDefault(size = 50, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Pageable boundedPageable = PageRequest.of(
                pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), 200),
                pageable.getSort()
        );
        return ResponseEntity.ok(ApiResponse.success("Get security logs successfully"
        ,securityLogService.getRecentLogs(boundedPageable)));
    }
}
