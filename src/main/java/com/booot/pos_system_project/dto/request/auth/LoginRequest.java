package com.booot.pos_system_project.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @NotNull(message = "Username must be not null")
    private String username;
    @NotNull(message = "Password must be not null")
    private String password;
}
