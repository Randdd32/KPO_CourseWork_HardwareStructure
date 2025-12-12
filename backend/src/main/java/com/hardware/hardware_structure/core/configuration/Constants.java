package com.hardware.hardware_structure.core.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String API_URL = "/api/v1";

    public static final String ADMIN_PREFIX = "/admin";

    public static final String LOGIN_URL = "/login";

    public  static final String AUTH_URL = "/auth";

    public static final String DEFAULT_PAGE_SIZE = "16";

    public static final Integer OTP_EXPIRATION_MINUTES = 10;

    public static final String OTP_EMAIL_SUBJECT = "Двухфакторная аутентификация: запрос на вход в Ваш аккаунт";

    public static final int REFRESH_TOKEN_LIMIT = 5;

    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+\\\\-]).{8,60}$";

    public static final String NAME_PATTERN = "^[A-Za-zА-Яа-яЁё\\-]{1,50}$";
}
