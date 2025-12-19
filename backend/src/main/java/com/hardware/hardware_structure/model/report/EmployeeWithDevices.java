package com.hardware.hardware_structure.model.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeWithDevices {
    private String employeeInfo;

    private List<DeviceShortInfo> devices;
}
