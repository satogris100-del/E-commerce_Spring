package com.booot.pos_system_project.mapper;

import com.booot.pos_system_project.dto.request.auth.UserRequest;
import com.booot.pos_system_project.dto.response.auth.UserResponse;
import com.booot.pos_system_project.enums.role.RoleUser;
import com.booot.pos_system_project.enums.user.UserStatus;
import com.booot.pos_system_project.model.User;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public final class UserMapper {

    private UserMapper() {}

    public static User toEntity(UserRequest request) {
        if (request == null) return null;

        User user = new User();
        user.setUsername(request.getUsername());
        user.setDisplayName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());   // raw here; encode it in the service
        user.setRole(request.getRole());
        return user;
    }

    public static UserResponse toResponse(User user) {
        if (user == null) return null;

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .profilePictureUrl(user.getProfilePictureUrl())
                .role(RoleUser.CUSTOMER)
                .status(UserStatus.DELETED)
                .lockTime(user.getLockTime())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static List<UserResponse> toResponseList(List<User> users) {
        return users.stream().map(UserMapper::toResponse).toList();
    }

    public static void updateEntity(User user, UserRequest request) {
        if (user == null || request == null) return;

        user.setDisplayName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
    }
}