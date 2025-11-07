package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.ToIntFunction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "device_model")
public class DeviceModelEntity extends BaseEntity {
    @Check(constraints = "length(name) >= 1")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_id_type", nullable = false)
    private DeviceTypeEntity type;

    @ManyToOne
    @JoinColumn(name = "fk_id_manufacturer", nullable = false)
    private ManufacturerEntity manufacturer;

    @OneToMany(mappedBy = "deviceModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private Set<DeviceModelStructureElementEntity> deviceModelStructure = new HashSet<>();

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DeviceEntity> devices = new HashSet<>();

    public DeviceModelEntity(String name, String description, DeviceTypeEntity type, ManufacturerEntity manufacturer) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.manufacturer = manufacturer;
    }

    public void addStructureElement(DeviceModelStructureElementEntity deviceModelStructureElement) {
        if (deviceModelStructureElement.getDeviceModel() != this) {
            deviceModelStructureElement.setDeviceModel(this);
        }
        deviceModelStructure.add(deviceModelStructureElement);
    }

    public void deleteStructureElement(DeviceModelStructureElementEntity deviceModelStructureElement) {
        if (deviceModelStructureElement.getDeviceModel() != this) {
            return;
        }
        deviceModelStructure.remove(deviceModelStructureElement);
    }

    public int getWorkEfficiency() {
        return computeAverage(StructureElementModelEntity::getWorkEfficiency);
    }

    public int getReliability() {
        return computeAverage(StructureElementModelEntity::getReliability);
    }

    public int getEnergyEfficiency() {
        return computeAverage(StructureElementModelEntity::getEnergyEfficiency);
    }

    public int getUserFriendliness() {
        return computeAverage(StructureElementModelEntity::getUserFriendliness);
    }

    public int getDurability() {
        return computeAverage(StructureElementModelEntity::getDurability);
    }

    public int getAestheticQualities() {
        return computeAverage(StructureElementModelEntity::getAestheticQualities);
    }

    private int computeAverage(ToIntFunction<StructureElementModelEntity> metricGetter) {
        return (int) deviceModelStructure.stream()
                .map(DeviceModelStructureElementEntity::getStructureElement)
                .mapToInt(metricGetter)
                .average()
                .orElse(0.0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, name, description, Optional.ofNullable(type).map(DeviceTypeEntity::getId).orElse(null),
                Optional.ofNullable(manufacturer).map(ManufacturerEntity::getId).orElse(null)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final DeviceModelEntity other = (DeviceModelEntity) obj;

        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.description, other.description)
                && Objects.equals(
                Optional.ofNullable(this.type).map(DeviceTypeEntity::getId).orElse(null),
                Optional.ofNullable(other.type).map(DeviceTypeEntity::getId).orElse(null))
                && Objects.equals(
                Optional.ofNullable(this.manufacturer).map(ManufacturerEntity::getId).orElse(null),
                Optional.ofNullable(other.manufacturer).map(ManufacturerEntity::getId).orElse(null));
    }
}
