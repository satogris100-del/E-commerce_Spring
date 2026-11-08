package com.booot.pos_system_project.controller.auth;

import com.booot.pos_system_project.dto.request.auth.UserRequest;
import com.booot.pos_system_project.dto.response.ApiResponse;
import com.booot.pos_system_project.dto.response.auth.UserResponse;
import com.booot.pos_system_project.enums.role.RoleUser;
import com.booot.pos_system_project.exception.BadRequestException;
import com.booot.pos_system_project.security.CustomUserDetailService.AppUSer;
import com.booot.pos_system_project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "User", description = "User Management APIs")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user (admin only)")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest userRequest
            ){
        return new ResponseEntity<>(ApiResponse.success("Create user successfully",
                userService.createUser(userRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get my profile")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(@AuthenticationPrincipal AppUSer user){
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", userService.getUserById(user.getId())));
    }

    @Operation(summary = "Upload or replace a user's profile picture")
    @PutMapping(value = "/{id}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> updateProfilePicture(
            @PathVariable Long id, @RequestPart("file") MultipartFile file
    ){
        return ResponseEntity.ok(ApiResponse.success("Update user's profile picture successfully", userService.updateProfilePicture(id, file)));
    }

    @Operation(summary = "Get user by ID (admin or the user themselves)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", userService.getUserById(id)));
    }
    @Operation(summary = "Get all users (admin only)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", userService.getAllUsers()));
    }

    @Operation(summary = "Update user by ID (admin or the user themselves)")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id, @Valid @RequestBody UserRequest userRequest
    ){
        return ResponseEntity.ok(ApiResponse.success("Update user successfully", userService.updateUser(id, userRequest)));
    }

    @Operation(summary = "Change a user's role (admin only)")
    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserRole(
            @PathVariable Long id, @AuthenticationPrincipal AppUSer actor,
            @RequestParam RoleUser role
            ){
        if(actor.getId().equals(id)){
            throw new BadRequestException("Administrators cannot change their own role");
        }
        return ResponseEntity.ok(ApiResponse.success("User role updated successfully", userService.updateUserRole(id, role)));
    }
    @Operation(summary = "Enable or disable an account (admin only)")
    @PatchMapping("/{id}/enabled")
    public ResponseEntity<ApiResponse<UserResponse>> enableAccount(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUSer actor,
            @RequestParam boolean enabled
    ){
        if(actor.getId().equals(id) && !enabled){
            throw new BadRequestException("Administrators cannot change their own Account!");
        }
        return ResponseEntity.ok(ApiResponse.success("User access Update successful!", userService.setUserEnabled(id, enabled)));
    }
    @Operation(summary = "Clear a user's failed-login lock (admin only)")
    @PatchMapping("/{id}/unlock")
    public ResponseEntity<ApiResponse<UserResponse>> clearFailedLoginLock(@PathVariable Long id){
        return new ResponseEntity<>(ApiResponse.success("Unlock User successful!", userService.unlockUser(id)), HttpStatus.OK);
    }

    @Operation(summary = "Update the authenticated user's profile")
    @PatchMapping("/{id}/profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest
    ){
        return ResponseEntity.ok(ApiResponse.success("Update Profile User successful!", userService.updateProfile(id, userRequest)));
    }

    @Operation(summary = "Delete user by ID (admin only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("Delete User successful!", null));

    }
}
