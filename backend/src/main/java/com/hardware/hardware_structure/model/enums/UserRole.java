package com.hardware.hardware_structure.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    SUPER_ADMIN,
    ADMIN,
    USER;

    private static final String PREFIX = "ROLE_";

    @Override
    public String getAuthority() {
        return PREFIX + this.name();
    }
}
