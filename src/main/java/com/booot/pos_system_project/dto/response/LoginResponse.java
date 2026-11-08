package com.booot.pos_system_project.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {

    private String username;
    private String token;
    private Set<String> roles;
}

