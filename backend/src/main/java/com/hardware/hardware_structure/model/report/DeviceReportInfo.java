package com.hardware.hardware_structure.model.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceReportInfo {
    private String serialNumber;

    private String modelName;

    private String purchaseDate;

    private double price;

    private String warrantyExpiryDate;

    private String state;

    private String locationInfo;
}
