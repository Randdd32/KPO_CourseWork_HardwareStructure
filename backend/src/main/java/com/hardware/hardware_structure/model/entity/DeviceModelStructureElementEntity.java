package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "device_model_structure")
public class DeviceModelStructureElementEntity {
    @EmbeddedId
    private DeviceModelStructureElementId id = new DeviceModelStructureElementId();

    @ManyToOne
    @MapsId("deviceModelId")
    @JoinColumn(name = "device_model_id", nullable = false)
    private DeviceModelEntity deviceModel;

    @ManyToOne
    @MapsId("structureElementModelId")
    @JoinColumn(name = "structure_element_model_id", nullable = false)
    private StructureElementModelEntity structureElement;

    @Check(constraints = "count > 0")
    @Column(nullable = false)
    private int count;

    public DeviceModelStructureElementEntity(DeviceModelEntity deviceModel, StructureElementModelEntity structureElement, int count) {
        this.deviceModel = deviceModel;
        this.structureElement = structureElement;
        this.count = count;
    }

    public void setDeviceModel(DeviceModelEntity deviceModel) {
        this.deviceModel = deviceModel;
        deviceModel.getDeviceModelStructure().add(this);
    }

    public void setStructureElement(StructureElementModelEntity structureElement) {
        this.structureElement = structureElement;
        structureElement.getDeviceModelStructure().add(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Optional.ofNullable(deviceModel).map(DeviceModelEntity::getId).orElse(null),
                Optional.ofNullable(structureElement).map(StructureElementModelEntity::getId).orElse(null), count);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        DeviceModelStructureElementEntity other = (DeviceModelStructureElementEntity) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(
                Optional.ofNullable(deviceModel).map(DeviceModelEntity::getId).orElse(null),
                Optional.ofNullable(other.deviceModel).map(DeviceModelEntity::getId).orElse(null))
                && Objects.equals(
                Optional.ofNullable(structureElement).map(StructureElementModelEntity::getId).orElse(null),
                Optional.ofNullable(other.structureElement).map(StructureElementModelEntity::getId).orElse(null))
                && count == other.count;
    }
}
