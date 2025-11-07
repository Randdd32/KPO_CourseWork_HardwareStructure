package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"employees", "departments"})
@Entity
@Table(name = "position")
public class PositionEntity extends BaseEntity {
    @Check(constraints = "length(name) >= 1")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "position")
    @OrderBy("id ASC")
    private Set<EmployeeEntity> employees = new HashSet<>();

    @ManyToMany(mappedBy = "positions")
    @OrderBy("id ASC")
    private Set<DepartmentEntity> departments = new HashSet<>();

    public PositionEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @PreRemove
    private void preRemove() {
        for (DepartmentEntity department: new HashSet<>(departments)) {
            department.getPositions().remove(this);
        }
        departments.clear();
        for (EmployeeEntity employee : employees) {
            employee.setPosition(null);
        }
    }
}
