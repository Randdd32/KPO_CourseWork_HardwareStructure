package com.hardware.hardware_structure.model.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceShortInfo {
    private String serialNumber;

    private String typeName;

    private String modelName;

    private String warrantyExpiryDate;

    private String state;
}
