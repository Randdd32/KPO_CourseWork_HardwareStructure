package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@EqualsAndHashCode(callSuper = true, exclude = {"positions", "employees", "locations"})
@Entity
@Table(name = "department")
public class DepartmentEntity extends BaseEntity {
    @Check(constraints = "length(name) >= 1")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "department_position",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    @OrderBy("id ASC")
    private Set<PositionEntity> positions = new HashSet<>();

    @OneToMany(mappedBy = "department")
    @OrderBy("id ASC")
    private Set<EmployeeEntity> employees = new HashSet<>();

    @OneToMany(mappedBy = "department")
    @OrderBy("id ASC")
    private Set<LocationEntity> locations = new HashSet<>();

    public DepartmentEntity(String name) {
        this.name = name;
    }

    public void addPosition(PositionEntity position) {
        this.positions.add(position);
        position.getDepartments().add(this);
    }

    public void deletePosition(PositionEntity position) {
        this.positions.remove(position);
        position.getDepartments().remove(this);
    }

    @PreRemove
    private void preRemove() {
        for (PositionEntity position : new HashSet<>(positions)) {
            position.getDepartments().remove(this);
        }
        positions.clear();
        for (EmployeeEntity employee : employees) {
            employee.setDepartment(null);
        }
        for (LocationEntity location : locations) {
            location.setDepartment(null);
        }
    }
}
