package com.hardware.hardware_structure.core.security;

import com.hardware.hardware_structure.core.utility.SecurityUtils;
import com.hardware.hardware_structure.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID format");
        }
        return SecurityUtils.convert(userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Bad user credentials")));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
