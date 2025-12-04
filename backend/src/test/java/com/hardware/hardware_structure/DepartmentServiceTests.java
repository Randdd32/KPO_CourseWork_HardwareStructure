package com.hardware.hardware_structure;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.service.entity.DepartmentService;
import com.hardware.hardware_structure.service.entity.PositionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

class DepartmentServiceTests extends AbstractIntegrationTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    private DepartmentEntity salesDepartment;
    private PositionEntity managerPosition;
    private PositionEntity directorPosition;
    private PositionEntity specialistPosition;

    @BeforeEach
    void createData() {
        managerPosition = positionService.create(new PositionEntity("Менеджер", ""));
        directorPosition = positionService.create(new PositionEntity("Директор", ""));
        specialistPosition = positionService.create(new PositionEntity("Специалист", ""));

        DepartmentEntity tempSales = new DepartmentEntity("Отдел продаж");
        tempSales.addPosition(managerPosition);
        tempSales.addPosition(directorPosition);
        salesDepartment = departmentService.create(tempSales);

        departmentService.create(new DepartmentEntity("Отдел логистики"));
    }

    @AfterEach
    void removeData() {
        departmentService.getAll(null).forEach(item -> departmentService.delete(item.getId()));
        positionService.getAll(null).forEach(item -> positionService.delete(item.getId()));
    }

    @Transactional
    @Test
    void getTest() {
        DepartmentEntity retrieved = departmentService.get(salesDepartment.getId());

        Assertions.assertEquals("Отдел продаж", retrieved.getName());
        Assertions.assertEquals(2, retrieved.getPositions().size());

        Assertions.assertThrows(NotFoundException.class, () -> departmentService.get(0L));
    }

    @Transactional
    @Test
    void createWithPositionsTest() {
        DepartmentEntity newDepartment = new DepartmentEntity("Отдел HR");
        newDepartment.addPosition(managerPosition);
        newDepartment.addPosition(specialistPosition);

        final DepartmentEntity createdDepartment = departmentService.create(newDepartment);

        Assertions.assertEquals(3, departmentService.getAll(null).size());
        Assertions.assertEquals(2, createdDepartment.getPositions().size());
        Assertions.assertTrue(createdDepartment.getPositions().contains(managerPosition));
    }

    @Test
    void createDuplicateTest() {
        final DepartmentEntity duplicateDepartment = new DepartmentEntity("Отдел продаж");
        Assertions.assertThrows(IllegalArgumentException.class, () -> departmentService.create(duplicateDepartment));
    }

    @Transactional
    @Test
    void updateWithPositionsTest() {
        final String newName = "Отдел продаж и маркетинга";

        DepartmentEntity updateEntity = new DepartmentEntity(newName);
        updateEntity.setPositions(Set.of(directorPosition, specialistPosition));

        final DepartmentEntity updatedDepartment = departmentService.update(salesDepartment.getId(), updateEntity);

        Assertions.assertEquals(newName, updatedDepartment.getName());
        Assertions.assertEquals(2, updatedDepartment.getPositions().size());
        Assertions.assertTrue(updatedDepartment.getPositions().contains(directorPosition));
        Assertions.assertTrue(updatedDepartment.getPositions().contains(specialistPosition));
        Assertions.assertFalse(updatedDepartment.getPositions().contains(managerPosition));
    }

    @Test
    void deleteTest() {
        departmentService.delete(salesDepartment.getId());
        Assertions.assertEquals(1, departmentService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> departmentService.get(salesDepartment.getId()));
    }
}
