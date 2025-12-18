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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "structure_element_model")
public class StructureElementModelEntity extends BaseEntity {
    @Check(constraints = "length(name) >= 1")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_id_type", nullable = false)
    private StructureElementTypeEntity type;

    @ManyToOne
    @JoinColumn(name = "fk_id_manufacturer", nullable = false)
    private ManufacturerEntity manufacturer;

    @Check(constraints = "work_efficiency >= 0 AND work_efficiency <= 100")
    @Column(name = "work_efficiency", nullable = false)
    private int workEfficiency;

    @Check(constraints = "reliability >= 0 AND reliability <= 100")
    @Column(nullable = false)
    private int reliability;

    @Check(constraints = "energy_efficiency >= 0 AND energy_efficiency <= 100")
    @Column(name = "energy_efficiency", nullable = false)
    private int energyEfficiency;

    @Check(constraints = "user_friendliness >= 0 AND user_friendliness <= 100")
    @Column(name = "user_friendliness", nullable = false)
    private int userFriendliness;

    @Check(constraints = "durability >= 0 AND durability <= 100")
    @Column(nullable = false)
    private int durability;

    @Check(constraints = "aesthetic_qualities >= 0 AND aesthetic_qualities <= 100")
    @Column(name = "aesthetic_qualities", nullable = false)
    private int aestheticQualities;

    @OneToMany(mappedBy = "structureElement", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private Set<DeviceModelStructureElementEntity> deviceModelStructure = new HashSet<>();

    public StructureElementModelEntity(String name, String description, StructureElementTypeEntity type,
                                       ManufacturerEntity manufacturer, int workEfficiency, int reliability,
                                       int energyEfficiency, int userFriendliness, int durability, int aestheticQualities) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.manufacturer = manufacturer;
        this.workEfficiency = workEfficiency;
        this.reliability = reliability;
        this.energyEfficiency = energyEfficiency;
        this.userFriendliness = userFriendliness;
        this.durability = durability;
        this.aestheticQualities = aestheticQualities;
    }

    public void addDeviceModel(DeviceModelStructureElementEntity deviceModelStructureElement) {
        if (deviceModelStructureElement.getStructureElement() != this) {
            deviceModelStructureElement.setStructureElement(this);
        }
        deviceModelStructure.add(deviceModelStructureElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, name, description, Optional.ofNullable(type).map(StructureElementTypeEntity::getId).orElse(null),
                Optional.ofNullable(manufacturer).map(ManufacturerEntity::getId).orElse(null), workEfficiency,
                reliability, energyEfficiency, userFriendliness, durability, aestheticQualities
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final StructureElementModelEntity other = (StructureElementModelEntity) obj;

        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.description, other.description)
                && Objects.equals(
                Optional.ofNullable(this.type).map(StructureElementTypeEntity::getId).orElse(null),
                Optional.ofNullable(other.type).map(StructureElementTypeEntity::getId).orElse(null))
                && Objects.equals(
                Optional.ofNullable(this.manufacturer).map(ManufacturerEntity::getId).orElse(null),
                Optional.ofNullable(other.manufacturer).map(ManufacturerEntity::getId).orElse(null))
                && this.workEfficiency == other.workEfficiency
                && this.reliability == other.reliability
                && this.energyEfficiency == other.energyEfficiency
                && this.userFriendliness == other.userFriendliness
                && this.durability == other.durability
                && this.aestheticQualities == other.aestheticQualities;
    }
}
