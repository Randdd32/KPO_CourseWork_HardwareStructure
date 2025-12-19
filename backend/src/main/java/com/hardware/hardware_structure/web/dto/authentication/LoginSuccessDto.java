package com.hardware.hardware_structure.web.dto.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSuccessDto {
    private String accessToken;

    private String refreshToken;
}
