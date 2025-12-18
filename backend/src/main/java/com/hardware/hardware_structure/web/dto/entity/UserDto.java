package com.hardware.hardware_structure.web.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(
            regexp = Constants.PASSWORD_PATTERN,
            message = "Password must be 8-60 characters, include at least one uppercase letter, one lowercase letter, one digit, and one special character (!@#$%^&*()-_+=;:,./?\\|`~[]{})."
    )
    private String password;

    @NotBlank
    @Pattern(
            regexp = Constants.PHONE_PATTERN,
            message = "Invalid phone number format"
    )
    private String phoneNumber;

    @NotNull
    private UserRole role;

    @NotNull
    @Min(1)
    private Long employeeId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String employeeFullName;
}
