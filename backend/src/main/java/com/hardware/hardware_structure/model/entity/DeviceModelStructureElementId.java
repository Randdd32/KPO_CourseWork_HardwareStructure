package com.hardware.hardware_structure.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DeviceModelStructureElementId implements Serializable {
    private Long deviceModelId;
    private Long structureElementModelId;
}
