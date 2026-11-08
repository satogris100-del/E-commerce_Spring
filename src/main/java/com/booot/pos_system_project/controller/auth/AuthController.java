package com.booot.pos_system_project.controller.auth;

import com.booot.pos_system_project.dto.request.auth.LoginRequest;
import com.booot.pos_system_project.dto.request.auth.RegisterRequest;
import com.booot.pos_system_project.dto.response.ApiResponse;
import com.booot.pos_system_project.dto.response.LoginResponse;
import com.booot.pos_system_project.dto.response.auth.MessageResponse;
import com.booot.pos_system_project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthController", description = "Login, Registration and Logout APIs")
@RequiredArgsConstructor
public class AuthController {
    private final UserService  userService;

    @PostMapping({"", "/register"})
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<MessageResponse>> register(
            @Valid @RequestBody RegisterRequest registerRequest
            ){
        MessageResponse register =userService.registerUser(registerRequest);
        return new ResponseEntity<>(ApiResponse.success("User registered successfully", register), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    @Operation(summary = "Login and obtain JWT token")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest  request
    ){
        request.setAttribute("auditActorUsername",  loginRequest.getUsername());
        LoginResponse login = userService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.success("Login successfully", login));
    }
    @PostMapping("/logout")
    @Operation(summary = "Logout and invalidate the current JWT token",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ){
        userService.logout(authorization);
        return ResponseEntity.ok(ApiResponse.success("Logout successfully", null));
    }
}
