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
import org.springframework.data.domain.Page;

import java.util.List;

class EmployeeServiceTests extends AbstractIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    private DepartmentEntity itDepartment;
    private DepartmentEntity hrDepartment;
    private PositionEntity engineerPosition;
    private PositionEntity hrPosition;
    private EmployeeEntity mainEmployee;

    @BeforeEach
    void createData() {
        itDepartment = departmentService.create(new DepartmentEntity("ИТ"));
        hrDepartment = departmentService.create(new DepartmentEntity("HR"));
        engineerPosition = positionService.create(new PositionEntity("Инженер", ""));
        hrPosition = positionService.create(new PositionEntity("Специалист по кадрам", ""));

        mainEmployee = employeeService.create(
                new EmployeeEntity("Иванов", "Иван", "Иванович", itDepartment, engineerPosition));

        employeeService.create(
                new EmployeeEntity("Петров", "Алексей", null, itDepartment, engineerPosition));

        employeeService.create(
                new EmployeeEntity("Сидорова", "Анна", "Сергеевна", hrDepartment, hrPosition));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(3, employeeService.getAll(null).size());
        Assertions.assertEquals("Иванов", employeeService.get(mainEmployee.getId()).getLastName());

        Assertions.assertThrows(NotFoundException.class, () -> employeeService.get(0L));
    }

    @Test
    void getAllWithSearchAndPaginationTest() {
        Page<EmployeeEntity> page = employeeService.getAll("Ива", null, 0, 10);
        Assertions.assertEquals(1, page.getTotalElements());
        Assertions.assertEquals("Иванов", page.getContent().get(0).getLastName());

        page = employeeService.getAll("Алексей", null, 0, 10);
        Assertions.assertEquals(1, page.getTotalElements());
        Assertions.assertEquals("Петров", page.getContent().get(0).getLastName());

        page = employeeService.getAll("ивАН", null, 0, 10);
        Assertions.assertEquals(1, page.getTotalElements());

        employeeService.create(new EmployeeEntity("Афанасьев", "Дмитрий", null, itDepartment, engineerPosition));
        employeeService.create(new EmployeeEntity("Васильев", "Егор", null, hrDepartment, hrPosition));
        // Всего 5 сотрудников

        // Страница 0, размер 2 (Сортировка по ID ASC: Иванов, Петров)
        page = employeeService.getAll(null, null, 0, 2);
        Assertions.assertEquals(5, page.getTotalElements());
        Assertions.assertEquals(2, page.getContent().size());
        Assertions.assertEquals("Иванов", page.getContent().get(0).getLastName());
        Assertions.assertEquals("Петров", page.getContent().get(1).getLastName());

        // Страница 2, размер 2 (последний элемент: Васильев)
        page = employeeService.getAll(null, null, 2, 2);
        Assertions.assertEquals(5, page.getTotalElements());
        Assertions.assertEquals(1, page.getContent().size());
        Assertions.assertEquals("Васильев", page.getContent().get(0).getLastName());

        // Создаем пользователя для "Иванов" (mainEmployee)
        userService.create(new UserEntity(
                "ivanov@test.com", "Passsss123!", "+79001112233",
                UserRole.USER,
                mainEmployee
        ));

        page = employeeService.getAll(null, true, 0, 10);
        Assertions.assertEquals(4, page.getTotalElements());
        boolean containsIvanov = page.getContent().stream()
                .anyMatch(e -> e.getLastName().equals("Иванов"));
        Assertions.assertFalse(containsIvanov, "Список не должен содержать сотрудника с аккаунтом при withoutAccount=true");

        page = employeeService.getAll(null, false, 0, 10);
        Assertions.assertEquals(1, page.getTotalElements());
        Assertions.assertEquals("Иванов", page.getContent().get(0).getLastName());
    }

    @Test
    void getByIdsTest() {
        List<EmployeeEntity> employees = employeeService.getByIds(List.of(mainEmployee.getId()));
        Assertions.assertEquals(1, employees.size());
        Assertions.assertEquals("Иванов", employees.get(0).getLastName());

        Assertions.assertTrue(employeeService.getByIds(null).isEmpty());
        Assertions.assertTrue(employeeService.getByIds(List.of()).isEmpty());
    }

    @Test
    void createTest() {
        final EmployeeEntity newEmployee = employeeService.create(
                new EmployeeEntity("Козлов", "Сергей", "Викторович", hrDepartment, engineerPosition));

        Assertions.assertEquals(4, employeeService.getAll(null).size());
        Assertions.assertEquals("Козлов", newEmployee.getLastName());

        final EmployeeEntity minimalEmployee = employeeService.create(
                new EmployeeEntity("Минин", "Кузьма", null, null, null));

        Assertions.assertEquals(5, employeeService.getAll(null).size());
        Assertions.assertNull(minimalEmployee.getDepartment());
        Assertions.assertNull(minimalEmployee.getPosition());
    }

    @Test
    void createInvalidDataTest() {
        // Валидация: пустое имя/фамилия
        final EmployeeEntity invalidLastName = new EmployeeEntity("", "Имя", "Отчество", itDepartment, engineerPosition);
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeService.create(invalidLastName));

        final EmployeeEntity invalidFirstName = new EmployeeEntity("Фамилия", " ", "Отчество", itDepartment, engineerPosition);
        Assertions.assertThrows(IllegalArgumentException.class, () -> employeeService.create(invalidFirstName));
    }

    @Test
    void updateTest() {
        final String newLastName = "Иванов-Смирнов";
        final String newFirstName = "Олег";
        final String newPatronymic = null;

        EmployeeEntity updateEntity = new EmployeeEntity(
                newLastName,
                newFirstName,
                newPatronymic,
                hrDepartment,
                hrPosition
        );

        final EmployeeEntity updatedEmployee = employeeService.update(mainEmployee.getId(), updateEntity);

        Assertions.assertEquals(newLastName, updatedEmployee.getLastName());
        Assertions.assertEquals(newFirstName, updatedEmployee.getFirstName());
        Assertions.assertNull(updatedEmployee.getPatronymic());
        Assertions.assertEquals(hrDepartment.getId(), updatedEmployee.getDepartment().getId());
        Assertions.assertEquals(hrPosition.getId(), updatedEmployee.getPosition().getId());
    }

    @Test
    void deleteTest() {
        employeeService.delete(mainEmployee.getId());
        Assertions.assertEquals(2, employeeService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> employeeService.get(mainEmployee.getId()));
    }
}
