package com.hardware.hardware_structure.core.utility;

import com.hardware.hardware_structure.model.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {
    public static User convert(UserEntity user) {
        return new User(user.getEmail(), user.getPassword(), Set.of(user.getRole()));
    }
}
