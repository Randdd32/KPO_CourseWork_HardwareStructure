package com.hardware.hardware_structure.web.dto.authentication;

import com.hardware.hardware_structure.core.configuration.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
            regexp = Constants.PASSWORD_PATTERN,
            message = "Password must be 8-60 characters, include at least one uppercase letter, one lowercase letter, one digit, and one special character (!@#$%^&*()-_+=;:,./?\\|`~[]{})."
    )
    private String password;
}
