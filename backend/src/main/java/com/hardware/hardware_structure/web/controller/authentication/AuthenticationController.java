package com.hardware.hardware_structure.web.controller.authentication;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.authentication.AuthenticationService;
import com.hardware.hardware_structure.service.entity.UserService;
import com.hardware.hardware_structure.web.dto.authentication.LoginRequestDto;
import com.hardware.hardware_structure.web.dto.authentication.LoginSuccessDto;
import com.hardware.hardware_structure.web.dto.authentication.OtpRequestDto;
import com.hardware.hardware_structure.web.dto.authentication.OtpVerificationRequestDto;
import com.hardware.hardware_structure.web.dto.authentication.RevokeTokenRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URL + Constants.AUTH_URL)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping(Constants.LOGIN_URL)
    public ResponseEntity<String> userLoginHandler(@RequestBody @Valid final LoginRequestDto userLoginRequestDto) {
        return ResponseEntity.ok(authenticationService.login(userLoginRequestDto));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Void> sendOtpHandler(@RequestBody @Valid final OtpRequestDto otpRequestDto) {
        authenticationService.sendOtp(userService.getByEmail(otpRequestDto.getEmail()), Constants.OTP_EMAIL_SUBJECT);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/invalidate-otp")
    public ResponseEntity<Void> invalidateOtpHandler(@RequestBody @Valid final OtpRequestDto otpRequestDto) {
        authenticationService.invalidateOtp(otpRequestDto.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<LoginSuccessDto> otpVerificationHandler(@RequestBody @Valid final OtpVerificationRequestDto otpVerificationRequestDto,
                                                                  HttpServletResponse response) {
        LoginSuccessDto loginSuccessDto = authenticationService.verifyOtp(otpVerificationRequestDto);

        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(loginSuccessDto.getRefreshToken());
        response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        loginSuccessDto.setRefreshToken(null);
        return ResponseEntity.ok(loginSuccessDto);
    }

    @PutMapping("/refresh-token")
    public ResponseEntity<LoginSuccessDto> tokenRefreshHandler(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                                                 HttpServletResponse response) {
        LoginSuccessDto loginSuccessDto = authenticationService.refreshToken(refreshToken);

        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(loginSuccessDto.getRefreshToken());
        response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        loginSuccessDto.setRefreshToken(null);
        return ResponseEntity.ok(loginSuccessDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutHandler(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                              HttpServletResponse response) {
        if (refreshToken != null) {
            authenticationService.logout(refreshToken);
        }

        ResponseCookie deleteRefreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(Constants.API_URL + Constants.AUTH_URL)
                .maxAge(0)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, deleteRefreshTokenCookie.toString());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutAllHandler(@RequestBody @Valid RevokeTokenRequestDto requestDto) {
        authenticationService.revokeAllUserTokens(requestDto.getEmail());
        return ResponseEntity.noContent().build();
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(Constants.API_URL + Constants.AUTH_URL)
                .maxAge(30 * 24 * 60 * 60) // 30 дней
                .build();
    }
}
