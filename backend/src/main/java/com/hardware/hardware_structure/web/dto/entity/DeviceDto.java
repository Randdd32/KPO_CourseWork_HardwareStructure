package com.hardware.hardware_structure.web.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hardware.hardware_structure.core.validation.IsoDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 15)
    private String serialNumber;

    @NotBlank
    @IsoDate
    private String purchaseDate;

    @NotBlank
    @IsoDate
    private String warrantyExpiryDate;

    @NotNull
    @Min(0)
    private Double price;

    @NotNull
    private Boolean isWorking;

    private String furtherInformation;

    @NotNull
    @Min(1)
    private Long modelId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modelName;

    @Min(1)
    private Long locationId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String locationName;

    @Min(1)
    private Long employeeId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String employeeFullName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String employeeInfo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String departmentName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String typeName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String manufacturerName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String buildingName;
}
