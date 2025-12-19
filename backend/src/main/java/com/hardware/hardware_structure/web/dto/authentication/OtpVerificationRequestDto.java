package com.hardware.hardware_structure.web.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerificationRequestDto {
    @NotBlank
    @Email
    private String email;

    @NotNull
    private Integer oneTimePassword;
}
