package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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

import com.hardware.hardware_structure.model.enums.LocationType;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "location")
public class LocationEntity extends BaseEntity {
    @Check(constraints = "length(name) >= 1")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType type;

    @ManyToOne
    @JoinColumn(name = "fk_id_building", nullable = false)
    private BuildingEntity building;

    @ManyToOne
    @JoinColumn(name = "fk_id_department")
    private DepartmentEntity department;

    @ManyToMany
    @JoinTable(
            name = "location_employee",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @OrderBy("id ASC")
    private Set<EmployeeEntity> employees = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @OrderBy("id ASC")
    private Set<DeviceEntity> devices = new HashSet<>();

    public LocationEntity(String name, LocationType type, BuildingEntity building, DepartmentEntity department) {
        this.name = name;
        this.type = type;
        this.building = building;
        this.department = department;
    }

    public void addEmployee(EmployeeEntity employee) {
        this.employees.add(employee);
        employee.getLocations().add(this);
    }

    public void deleteEmployee(EmployeeEntity employee) {
        this.employees.remove(employee);
        employee.getLocations().remove(this);
    }

    @PreRemove
    private void preRemove() {
        for (EmployeeEntity employee : new HashSet<>(employees)) {
            employee.getLocations().remove(this);
        }
        employees.clear();
        for (DeviceEntity device : devices) {
            device.setLocation(null);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, name, type, Optional.ofNullable(building).map(BuildingEntity::getId).orElse(null),
                Optional.ofNullable(department).map(DepartmentEntity::getId).orElse(null)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final LocationEntity other = (LocationEntity) obj;

        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.name, other.name)
                && this.type == other.type
                && Objects.equals(
                Optional.ofNullable(this.building).map(BuildingEntity::getId).orElse(null),
                Optional.ofNullable(other.building).map(BuildingEntity::getId).orElse(null))
                && Objects.equals(
                Optional.ofNullable(this.department).map(DepartmentEntity::getId).orElse(null),
                Optional.ofNullable(other.department).map(DepartmentEntity::getId).orElse(null));
    }
}
