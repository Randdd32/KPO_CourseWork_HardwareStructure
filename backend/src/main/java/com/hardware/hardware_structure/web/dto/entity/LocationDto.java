package com.hardware.hardware_structure.web.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hardware.hardware_structure.model.enums.LocationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nameWithBuilding;

    @NotNull
    private LocationType type;

    @NotNull
    @Min(1)
    private Long buildingId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String buildingName;

    @Min(1)
    private Long departmentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String departmentName;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> employeeIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<EmployeeDto> employees;
}
