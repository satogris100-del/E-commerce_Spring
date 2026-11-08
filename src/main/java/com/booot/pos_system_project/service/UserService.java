package com.booot.pos_system_project.service;

import com.booot.pos_system_project.dto.request.auth.LoginRequest;
import com.booot.pos_system_project.dto.request.auth.RegisterRequest;
import com.booot.pos_system_project.dto.request.auth.UserRequest;
import com.booot.pos_system_project.dto.response.LoginResponse;
import com.booot.pos_system_project.dto.response.auth.MessageResponse;
import com.booot.pos_system_project.dto.response.auth.UserResponse;
import com.booot.pos_system_project.enums.role.RoleUser;
import com.booot.pos_system_project.model.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    LoginResponse login(LoginRequest loginRequest);

    MessageResponse registerUser(RegisterRequest  registerRequest);
    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id,UserRequest userRequest);
    UserResponse updateUserRole(Long id, RoleUser role);
    UserResponse setUserEnabled(Long id, boolean enabled);

    UserResponse unlockUser(Long id);
    UserResponse updateProfile(Long id, UserRequest userRequest);
    UserResponse updateProfilePicture(Long id, MultipartFile file);

    void deleteUser(Long id);
    void logout(String authorizationHeader);

}
