package com.hardware.hardware_structure.web.dto.email;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {
    @Email
    private String from;

    @Email
    private String to;

    private String subject;

    private String message;
}
