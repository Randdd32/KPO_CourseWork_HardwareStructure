package com.hardware.hardware_structure.model.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationEmployeesWithDevices {
    private String extendedLocationName;

    private List<EmployeeWithDevices> employees;
}
