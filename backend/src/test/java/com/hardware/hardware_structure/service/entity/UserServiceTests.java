package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.model.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTests extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private EmployeeEntity employeeForMainUser;
    private EmployeeEntity employeeForSecondUser;
    private UserEntity mainUser;

    @BeforeEach
    void createData() {
        DepartmentEntity department = departmentService.create(new DepartmentEntity("IT"));
        PositionEntity position = positionService.create(new PositionEntity("Программист", ""));

        employeeForMainUser = employeeService.create(
                new EmployeeEntity("Админов", "Админ", null, department, position));

        employeeForSecondUser = employeeService.create(
                new EmployeeEntity("Юзеров", "Юзер", null, department, position));

        mainUser = userService.create(new UserEntity(
                "admin@example.com",
                "StrongPass1!",
                "+79001112233",
                UserRole.SUPER_ADMIN,
                employeeForMainUser
        ));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(1, userService.getAll().size());

        UserEntity foundById = userService.get(mainUser.getId());
        Assertions.assertEquals("admin@example.com", foundById.getEmail());

        UserEntity foundByEmail = userService.getByEmail("ADMIN@example.com");
        Assertions.assertEquals(mainUser.getId(), foundByEmail.getId());

        Assertions.assertThrows(NotFoundException.class, () -> userService.get(0L));
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.getByEmail("notfound@example.com"));
    }

    @Test
    void createTest() {
        UserEntity newUser = userService.create(new UserEntity(
                "user@example.com",
                "StrongPass1!",
                "8 (900) 55-56-677",
                UserRole.USER,
                employeeForSecondUser
        ));

        Assertions.assertEquals(2, userService.getAll().size());
        Assertions.assertEquals("+79005556677", newUser.getPhoneNumber()); // Должен нормализоваться

        // Проверяем, что пароль захэширован
        Assertions.assertNotEquals("StrongPass1!", newUser.getPassword());
        Assertions.assertTrue(passwordEncoder.matches("StrongPass1!", newUser.getPassword()));
    }

    @Test
    void createInvalidDataTest() {
        UserEntity duplicateEmailUser = new UserEntity(
                "admin@example.com", "Passsss1!", "+79990000000", UserRole.USER, employeeForSecondUser
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.create(duplicateEmailUser));

        UserEntity invalidEmailUser = new UserEntity(
                "invalid-email", "Passsss1!", "+79990000000", UserRole.USER, employeeForSecondUser
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.create(invalidEmailUser));

        UserEntity invalidPhoneUser = new UserEntity(
                "new@example.com", "Passsss1!", "12345", UserRole.USER, employeeForSecondUser
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.create(invalidPhoneUser));

        UserEntity weakPassUser = new UserEntity(
                "new@example.com", "123", "+79990000000", UserRole.USER, employeeForSecondUser
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.create(weakPassUser));
    }

    @Test
    void updateTest() {
        final String newEmail = "updated@example.com";

        UserEntity updateEntity = new UserEntity();
        updateEntity.setEmail(newEmail);
        updateEntity.setPhoneNumber("89998887766");
        updateEntity.setRole(UserRole.USER);
        updateEntity.setEmployee(employeeForSecondUser);
        updateEntity.setPassword("NewStrongPass1!");

        final UserEntity updatedUser = userService.update(mainUser.getId(), updateEntity);

        Assertions.assertEquals(newEmail, updatedUser.getEmail());
        Assertions.assertEquals("+79998887766", updatedUser.getPhoneNumber());
        Assertions.assertEquals(UserRole.USER, updatedUser.getRole());
        Assertions.assertEquals(employeeForSecondUser, updatedUser.getEmployee());
        Assertions.assertTrue(passwordEncoder.matches("NewStrongPass1!", updatedUser.getPassword()));
    }

    @Test
    void updateWithoutPasswordChangeTest() {
        UserEntity updateEntity = new UserEntity();
        updateEntity.setEmail(mainUser.getEmail());
        updateEntity.setPhoneNumber(mainUser.getPhoneNumber());
        updateEntity.setRole(mainUser.getRole());
        updateEntity.setEmployee(mainUser.getEmployee());
        updateEntity.setPassword(null);

        UserEntity updatedUser = userService.update(mainUser.getId(), updateEntity);

        // Пароль должен остаться старым
        Assertions.assertTrue(passwordEncoder.matches("StrongPass1!", updatedUser.getPassword()));
    }

    @Test
    void deleteTest() {
        userService.delete(mainUser.getId());
        Assertions.assertEquals(0, userService.getAll().size());
        Assertions.assertThrows(NotFoundException.class, () -> userService.get(mainUser.getId()));
    }
}
