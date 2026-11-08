package com.booot.pos_system_project.dto.response.auth;


import com.booot.pos_system_project.enums.role.RoleUser;
import com.booot.pos_system_project.enums.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String displayName;
    private String email;
    private String profilePictureUrl;
    private RoleUser role;
    private UserStatus status;
    private LocalDateTime lockTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}