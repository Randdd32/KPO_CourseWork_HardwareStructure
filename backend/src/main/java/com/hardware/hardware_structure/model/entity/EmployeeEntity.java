package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity extends BaseEntity {
    @Check(constraints = "length(last_name) >= 1")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Check(constraints = "length(first_name) >= 1")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String patronymic;

    @ManyToOne
    @JoinColumn(name = "fk_id_department")
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "fk_id_position")
    private PositionEntity position;

    @ManyToMany(mappedBy = "employees")
    @OrderBy("id ASC")
    private Set<LocationEntity> locations = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @OrderBy("id ASC")
    private Set<DeviceEntity> devices = new HashSet<>();

    public EmployeeEntity(String lastName, String firstName, String patronymic,
                          DepartmentEntity department, PositionEntity position) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.department = department;
        this.position = position;
    }

    public String getFullName() {
        return patronymic == null || patronymic.isBlank()
                ? String.format("%s %s", lastName, firstName)
                : String.format("%s %s %s", lastName, firstName, patronymic);
    }

    @PreRemove
    private void preRemove() {
        for (LocationEntity location : new HashSet<>(locations)) {
            location.getEmployees().remove(this);
        }
        locations.clear();
        for (DeviceEntity device : devices) {
            device.setEmployee(null);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, lastName, firstName, patronymic, Optional.ofNullable(department).map(DepartmentEntity::getId).orElse(null),
                Optional.ofNullable(position).map(PositionEntity::getId).orElse(null)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final EmployeeEntity other = (EmployeeEntity) obj;

        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.lastName, other.lastName)
                && Objects.equals(this.firstName, other.firstName)
                && Objects.equals(this.patronymic, other.patronymic)
                && Objects.equals(
                Optional.ofNullable(this.department).map(DepartmentEntity::getId).orElse(null),
                Optional.ofNullable(other.department).map(DepartmentEntity::getId).orElse(null))
                && Objects.equals(
                Optional.ofNullable(this.position).map(PositionEntity::getId).orElse(null),
                Optional.ofNullable(other.position).map(PositionEntity::getId).orElse(null));
    }
}
