package com.booot.pos_system_project.model;

import com.booot.pos_system_project.enums.common.ActiveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "category_tbl",
        indexes = @Index(name = "idx_category_parent", columnList = "parent_id"))
@Getter
@Setter
@NoArgsConstructor
public class Category extends Base {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 180)
    private String slug;

    @Column(length = 500)
    private String description;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private Integer sortOrder = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ActiveStatus status = ActiveStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy("sortOrder ASC")
    private Set<Category> children = new LinkedHashSet<>();

    public void addChild(Category child) {
        children.add(child);
        child.setParent(this);
    }
}