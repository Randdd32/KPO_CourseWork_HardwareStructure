package com.hardware.hardware_structure.web.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hardware.hardware_structure.core.configuration.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Pattern(
            regexp = Constants.NAME_PATTERN,
            message = "Last name must be 1-50 characters long and contain only letters and hyphens"
    )
    private String lastName;

    @NotBlank
    @Pattern(
            regexp = Constants.NAME_PATTERN,
            message = "First name must be 1-50 characters long and contain only letters and hyphens"
    )
    private String firstName;

    @Pattern(
            regexp = Constants.NAME_PATTERN,
            message = "Patronymic must be up to 50 characters long and contain only letters and hyphens"
    )
    private String patronymic;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fullName;

    @Min(1)
    private Long departmentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String departmentName;

    @Min(1)
    private Long positionId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String positionName;
}
