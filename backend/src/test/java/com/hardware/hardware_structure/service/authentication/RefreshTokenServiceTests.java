package com.hardware.hardware_structure.service.authentication;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.model.authentication.RefreshTokenEntity;
import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.model.enums.UserRole;
import com.hardware.hardware_structure.repository.RefreshTokenRepository;
import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.service.entity.DepartmentService;
import com.hardware.hardware_structure.service.entity.EmployeeService;
import com.hardware.hardware_structure.service.entity.PositionService;
import com.hardware.hardware_structure.service.entity.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.UUID;

class RefreshTokenServiceTests extends AbstractIntegrationTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;

    private UserEntity user;

    @BeforeEach
    void setup() {
        DepartmentEntity dep = departmentService.create(new DepartmentEntity("IT"));
        PositionEntity pos = positionService.create(new PositionEntity("Dev", ""));
        EmployeeEntity emp = employeeService.create(new EmployeeEntity("Test", "User", null, dep, pos));

        user = userService.create(new UserEntity("token_test@example.com", "Passssss123!", "+79990000000", UserRole.USER, emp));
    }

    @Test
    void createTokenTest() {
        String tokenValue = UUID.randomUUID().toString();
        refreshTokenService.createToken(user, tokenValue);

        RefreshTokenEntity retrieved = refreshTokenService.validateAndGetToken(tokenValue);
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals(user.getId(), retrieved.getUser().getId());
        Assertions.assertEquals(tokenValue, retrieved.getToken());
    }

    @Test
    void createTokenLimitTest() {
        int limit = Constants.REFRESH_TOKEN_LIMIT;

        for (int i = 0; i < limit + 2; i++) {
            refreshTokenService.createToken(user, "token_" + i);
        }

        List<RefreshTokenEntity> tokens = refreshTokenRepository.findAllByUser(user);


        Assertions.assertTrue(tokens.size() <= limit, "Количество токенов не должно превышать лимит или должно сбрасываться");
        RefreshTokenEntity lastToken = refreshTokenService.validateAndGetToken("token_" + (limit + 1));
        Assertions.assertNotNull(lastToken);
    }

    @Test
    void validateTokenNotFoundTest() {
        Assertions.assertThrows(BadCredentialsException.class, () ->
                refreshTokenService.validateAndGetToken("non_existent_token")
        );
    }

    @Test
    void deleteTokenTest() {
        String token = "delete_me";
        refreshTokenService.createToken(user, token);

        refreshTokenService.deleteToken(token);

        Assertions.assertThrows(BadCredentialsException.class, () ->
                refreshTokenService.validateAndGetToken(token)
        );
    }

    @Test
    void deleteAllUserTokensTest() {
        refreshTokenService.createToken(user, "t1");
        refreshTokenService.createToken(user, "t2");

        refreshTokenService.deleteAllUserTokens(user);

        Assertions.assertTrue(refreshTokenRepository.findAllByUser(user).isEmpty());
    }
}
