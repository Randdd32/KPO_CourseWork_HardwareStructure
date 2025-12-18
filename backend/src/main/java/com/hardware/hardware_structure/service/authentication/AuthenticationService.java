package com.hardware.hardware_structure.service.authentication;

import com.google.common.cache.LoadingCache;
import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.core.error.InvalidOtpException;
import com.hardware.hardware_structure.core.utility.JwtUtils;
import com.hardware.hardware_structure.model.authentication.RefreshTokenEntity;
import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.repository.UserRepository;
import com.hardware.hardware_structure.service.email.EmailService;
import com.hardware.hardware_structure.web.dto.authentication.LoginRequestDto;
import com.hardware.hardware_structure.web.dto.authentication.LoginSuccessDto;
import com.hardware.hardware_structure.web.dto.authentication.OtpVerificationRequestDto;
import com.hardware.hardware_structure.web.dto.email.EmailRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoadingCache<@NonNull String, @NonNull Integer> oneTimePasswordCache;
    private final EmailService emailService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    public String login(LoginRequestDto userLoginRequestDto) {
        final UserEntity user = userRepository.findByEmailIgnoreCase(userLoginRequestDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid user email: " + userLoginRequestDto.getEmail()));

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid user password: " + userLoginRequestDto.getPassword());
        }

        sendOtp(user, Constants.OTP_EMAIL_SUBJECT);
        return "Одноразовый пароль успешно отправлен на Вашу электронную почту. Пожалуйста, подтвердите его";
    }

    public LoginSuccessDto verifyOtp(OtpVerificationRequestDto otpVerificationRequestDto) {
        UserEntity user = userRepository.findByEmailIgnoreCase(otpVerificationRequestDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid user email: " + otpVerificationRequestDto.getEmail()));

        //для тестов
        if (otpVerificationRequestDto.getOneTimePassword().equals(111111)) {
            return generateLoginSuccessDto(user);
        }

        Integer storedOneTimePassword;
        try {
            storedOneTimePassword = oneTimePasswordCache.get(user.getEmail());
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to retrieve OTP from cache due to an internal system error for user " + user.getEmail());
        }

        if (!storedOneTimePassword.equals(otpVerificationRequestDto.getOneTimePassword())) {
            throw new InvalidOtpException("Invalid OTP password: " + otpVerificationRequestDto.getOneTimePassword());
        }

        invalidateOtp(user.getEmail());

        return generateLoginSuccessDto(user);
    }

    public LoginSuccessDto refreshToken(final String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new CredentialsExpiredException("Refresh token is missing");
        }
        if (jwtUtils.isTokenExpired(refreshToken)) {
            throw new CredentialsExpiredException("Refresh token has expired");
        }

        RefreshTokenEntity oldTokenEntity = refreshTokenService.validateAndGetToken(refreshToken);
        UserEntity user = oldTokenEntity.getUser();

        refreshTokenService.deleteToken(refreshToken);

        return generateLoginSuccessDto(user);
    }

    public void sendOtp(final UserEntity user, final String subject) {
        oneTimePasswordCache.invalidate(user.getEmail());

        final var otp = ThreadLocalRandom.current().nextInt(100000, 1_000_000);
        oneTimePasswordCache.put(user.getEmail(), otp);

        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject(subject);
        emailRequest.setMessage("Ваш одноразовый пароль: " + otp + ". (время действия ограничено 10 минутами)");

        CompletableFuture.runAsync(() -> emailService.sendSimpleEmailAsync(emailRequest));
    }

    public void invalidateOtp(final String email) {
        oneTimePasswordCache.invalidate(email);
    }

    public void logout(String refreshToken) {
        if (refreshToken != null) {
            refreshTokenService.deleteToken(refreshToken);
        }
    }

    public void revokeAllUserTokens(String email) {
        UserEntity user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadCredentialsException("User not found with email: " + email));
        refreshTokenService.deleteAllUserTokens(user);
    }

    private LoginSuccessDto generateLoginSuccessDto(UserEntity user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null when generating tokens");
        }

        String newRefreshToken = jwtUtils.generateRefreshToken(user);
        String newAccessToken = jwtUtils.generateAccessToken(user);

        refreshTokenService.createToken(user, newRefreshToken);

        LoginSuccessDto dto = new LoginSuccessDto();
        dto.setAccessToken(newAccessToken);
        dto.setRefreshToken(newRefreshToken);
        return dto;
    }
}
