package com.hardware.hardware_structure.core.security.enums;

import com.hardware.hardware_structure.core.configuration.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiPathExclusion {
    WEBJARS("/webjars/**"),
    STATICS("/static/**"),
    LOGIN(Constants.API_URL + Constants.AUTH_URL + Constants.LOGIN_URL),
    OTP_VERIFICATION(Constants.API_URL + Constants.AUTH_URL + "/verify-otp"),
    SEND_OTP(Constants.API_URL + Constants.AUTH_URL + "/send-otp"),
    INVALIDATE_OTP(Constants.API_URL + Constants.AUTH_URL + "/invalidate-otp"),
    REFRESH_TOKEN(Constants.API_URL + Constants.AUTH_URL + "/refresh-token"),
    LOGOUT(Constants.API_URL + Constants.AUTH_URL + "/logout"),
    LOGOUT_ALL(Constants.API_URL + Constants.AUTH_URL + "/logout-all"),
    ACTUATOR("/actuator/**"),
    SWAGGER_UI("/swagger-ui/**"),
    SWAGGER_UI_HTML("/swagger-ui.html"),
    API_DOCS("/v3/api-docs/**");

    private final String path;
}
