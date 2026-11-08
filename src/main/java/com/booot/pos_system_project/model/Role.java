package com.booot.pos_system_project.model;

import com.booot.pos_system_project.enums.role.RoleUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "name", unique = true)
    private RoleUser name;

    public Role(RoleUser name) {
        this.name = name;
    }
}
