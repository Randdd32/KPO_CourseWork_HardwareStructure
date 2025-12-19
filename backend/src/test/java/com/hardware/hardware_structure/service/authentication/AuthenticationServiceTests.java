package com.hardware.hardware_structure.service.authentication;

import com.google.common.cache.LoadingCache;
import com.hardware.hardware_structure.core.error.InvalidOtpException;
import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.model.enums.UserRole;
import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.service.entity.DepartmentService;
import com.hardware.hardware_structure.service.entity.EmployeeService;
import com.hardware.hardware_structure.service.entity.PositionService;
import com.hardware.hardware_structure.service.entity.UserService;
import com.hardware.hardware_structure.web.dto.authentication.LoginRequestDto;
import com.hardware.hardware_structure.web.dto.authentication.LoginSuccessDto;
import com.hardware.hardware_structure.web.dto.authentication.OtpVerificationRequestDto;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

class AuthenticationServiceTests extends AbstractIntegrationTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LoadingCache<@NonNull String, @NonNull Integer> oneTimePasswordCache;

    @Autowired
    private JavaMailSender javaMailSender;

    private UserEntity user;
    private final String PASSWORD = "StrongPass1!";
    private final String EMAIL = "auth_test@example.com";

    @BeforeEach
    void setup() {
        DepartmentEntity dep = departmentService.create(new DepartmentEntity("IT"));
        PositionEntity pos = positionService.create(new PositionEntity("Dev", ""));
        EmployeeEntity emp = employeeService.create(new EmployeeEntity("Auth", "User", null, dep, pos));

        user = userService.create(new UserEntity(EMAIL, PASSWORD, "+79001112233", UserRole.USER, emp));
    }

    @Test
    void fullLoginCycleTest() throws ExecutionException {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail(EMAIL);
        loginRequest.setPassword(PASSWORD);

        String responseMsg = authenticationService.login(loginRequest);
        Assertions.assertNotNull(responseMsg);

        verify(javaMailSender, timeout(2000).times(1)).send(any(SimpleMailMessage.class));

        Integer otp = oneTimePasswordCache.get(EMAIL);
        Assertions.assertNotNull(otp);

        OtpVerificationRequestDto otpRequest = new OtpVerificationRequestDto();
        otpRequest.setEmail(EMAIL);
        otpRequest.setOneTimePassword(otp);

        LoginSuccessDto tokens = authenticationService.verifyOtp(otpRequest);

        Assertions.assertNotNull(tokens.getAccessToken());
        Assertions.assertNotNull(tokens.getRefreshToken());

        LoginSuccessDto newTokens = authenticationService.refreshToken(tokens.getRefreshToken());

        Assertions.assertNotNull(newTokens.getAccessToken());
        Assertions.assertNotNull(newTokens.getRefreshToken());
        Assertions.assertNotEquals(tokens.getRefreshToken(), newTokens.getRefreshToken());
    }

    @Test
    void loginInvalidPasswordTest() {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail(EMAIL);
        loginRequest.setPassword("WrongPass!");

        Assertions.assertThrows(BadCredentialsException.class, () ->
                authenticationService.login(loginRequest)
        );
    }

    @Test
    void verifyInvalidOtpTest() {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail(EMAIL);
        loginRequest.setPassword(PASSWORD);
        authenticationService.login(loginRequest);

        OtpVerificationRequestDto otpRequest = new OtpVerificationRequestDto();
        otpRequest.setEmail(EMAIL);
        otpRequest.setOneTimePassword(12345);

        Assertions.assertThrows(InvalidOtpException.class, () ->
                authenticationService.verifyOtp(otpRequest)
        );
    }

    @Test
    void logoutTest() throws ExecutionException {
        authenticationService.sendOtp(user, "Test Subject");
        Integer otp = oneTimePasswordCache.get(EMAIL);
        OtpVerificationRequestDto otpRequest = new OtpVerificationRequestDto();
        otpRequest.setEmail(EMAIL);
        otpRequest.setOneTimePassword(otp);

        LoginSuccessDto tokens = authenticationService.verifyOtp(otpRequest);

        authenticationService.logout(tokens.getRefreshToken());

        Assertions.assertThrows(BadCredentialsException.class, () ->
                authenticationService.refreshToken(tokens.getRefreshToken())
        );
    }
}
