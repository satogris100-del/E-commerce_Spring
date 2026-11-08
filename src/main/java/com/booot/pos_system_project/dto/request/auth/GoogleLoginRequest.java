package com.booot.pos_system_project.dto.request.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleLoginRequest {
    @NotBlank(message = "Google ID token is required")
    private String idToken;

    public String getIdToken(){
        return idToken;
    }
    public void setIdToken(String idToken){
        this.idToken = idToken;
    }
}
