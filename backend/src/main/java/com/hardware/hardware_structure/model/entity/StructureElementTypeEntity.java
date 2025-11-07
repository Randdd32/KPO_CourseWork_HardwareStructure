package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@EqualsAndHashCode(callSuper = true, exclude = "structureElementModels")
@Entity
@Table(name = "structure_element_type")
public class StructureElementTypeEntity extends BaseEntity {
    @Check(constraints = "length(name) >= 1")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StructureElementModelEntity> structureElementModels = new HashSet<>();

    public StructureElementTypeEntity(String name) {
        this.name = name;
    }
}
