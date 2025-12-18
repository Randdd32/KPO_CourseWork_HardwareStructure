package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Check(constraints = "warranty_expire_date >= purchase_date")
@Entity
@Table(name = "device")
public class DeviceEntity extends BaseEntity {
    @Check(constraints = "length(serial_number) >= 1")
    @Column(name = "serial_number", nullable = false, unique = true, length = 15)
    private String serialNumber;

    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "warranty_expire_date", nullable = false)
    private Date warrantyExpiryDate;

    @Check(constraints = "price >= 0")
    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean isWorking = true;

    @Column(columnDefinition = "text")
    private String furtherInformation;

    @ManyToOne
    @JoinColumn(name = "fk_id_model", nullable = false)
    private DeviceModelEntity model;

    @ManyToOne
    @JoinColumn(name = "fk_id_location")
    private LocationEntity location;

    @ManyToOne
    @JoinColumn(name = "fk_id_employee")
    private EmployeeEntity employee;

    public DeviceEntity(String serialNumber, Date purchaseDate, Date warrantyExpiryDate, double price, boolean isWorking,
                        String furtherInformation, DeviceModelEntity model, LocationEntity location, EmployeeEntity employee) {
        this.serialNumber = serialNumber;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiryDate = warrantyExpiryDate;
        this.price = price;
        this.isWorking = isWorking;
        this.furtherInformation = furtherInformation;
        this.model = model;
        this.location = location;
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, purchaseDate, warrantyExpiryDate, price, isWorking, furtherInformation,
                Optional.ofNullable(model).map(DeviceModelEntity::getId).orElse(null), Optional.ofNullable(location).map(LocationEntity::getId).orElse(null),
                Optional.ofNullable(employee).map(EmployeeEntity::getId).orElse(null)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final DeviceEntity other = (DeviceEntity) obj;

        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.serialNumber, other.serialNumber)
                && Objects.equals(this.purchaseDate, other.purchaseDate)
                && Objects.equals(this.warrantyExpiryDate, other.warrantyExpiryDate)
                && Double.compare(this.price, other.price) == 0
                && this.isWorking == other.isWorking
                && Objects.equals(this.furtherInformation, other.furtherInformation)
                && Objects.equals(
                Optional.ofNullable(this.model).map(DeviceModelEntity::getId).orElse(null),
                Optional.ofNullable(other.model).map(DeviceModelEntity::getId).orElse(null))
                && Objects.equals(
                Optional.ofNullable(this.location).map(LocationEntity::getId).orElse(null),
                Optional.ofNullable(other.location).map(LocationEntity::getId).orElse(null))
                && Objects.equals(
                Optional.ofNullable(this.employee).map(EmployeeEntity::getId).orElse(null),
                Optional.ofNullable(other.employee).map(EmployeeEntity::getId).orElse(null));
    }
}
