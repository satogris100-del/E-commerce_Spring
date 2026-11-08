package com.booot.pos_system_project.model;

import com.booot.pos_system_project.enums.role.AuthProvider;
import com.booot.pos_system_project.enums.role.RoleUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "display_name")
    private String displayName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "profile_picture_url", length = 2048)
    private String profilePictureUrl;

    @Column(name = "profile_picture_public_id")
    private String profilePicturePublicId;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = true;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthProvider provider = AuthProvider.LOCAL;

    @Column(name = "provider_id", unique = true)
    private String providerId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public String getName() {
        return this.username;
    }

    public void setName(String name) {
        this.username = name;
    }

    private RoleUser getRoleName(Role role) {
        return role == null ? null : role.getName();
    }

    public String getRole() {
        if (roles == null || roles.isEmpty()) {
            return RoleUser.CUSTOMER.name();
        }
        Set<RoleUser> names = roles.stream()
                .filter(java.util.Objects::nonNull)
                .map(this::getRoleName)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());
        if(names.contains(RoleUser.ADMIN))return RoleUser.ADMIN.name();
        if(names.contains(RoleUser.STAFF)) return RoleUser.STAFF.name();

        return RoleUser.STAFF.name();
    }

    public void setRole(String role) {
        this.roles = new HashSet<>();
        if (role == null || role.isBlank()) {
            return;
        }

        RoleUser normalizedRole = RoleUser.valueOf(role.trim().toUpperCase());
        this.roles.add(new Role(normalizedRole));
    }

    public LocalDateTime getCreateAt() {
        return this.createdAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return this.updatedAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updatedAt = updateAt;
    }

}
