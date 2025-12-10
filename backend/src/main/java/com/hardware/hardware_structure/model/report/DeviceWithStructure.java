package com.hardware.hardware_structure.model.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeviceWithStructure {
    private String serialNumber;

    private String modelName;

    private String typeName;

    private int workEfficiency;

    private int reliability;

    private int energyEfficiency;

    private int userFriendliness;

    private int durability;

    private int aestheticQualities;

    private List<StructureElementInfo> structureElementsInfo;
}
