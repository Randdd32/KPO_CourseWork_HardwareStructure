package com.hardware.hardware_structure;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.StructureElementTypeEntity;
import com.hardware.hardware_structure.service.entity.StructureElementTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class StructureElementTypeServiceTests extends AbstractIntegrationTest {

    @Autowired
    private StructureElementTypeService typeService;

    private StructureElementTypeEntity cpuType;

    @BeforeEach
    void createData() {
        cpuType = typeService.create(new StructureElementTypeEntity("Процессор"));
        typeService.create(new StructureElementTypeEntity("Оперативная память"));
        typeService.create(new StructureElementTypeEntity("Видеокарта"));
        typeService.create(new StructureElementTypeEntity("Корпус"));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(4, typeService.getAll(null).size());
        Assertions.assertEquals("Процессор", typeService.get(cpuType.getId()).getName());

        Assertions.assertThrows(NotFoundException.class, () -> typeService.get(0L));
    }

    @Test
    void createTest() {
        final StructureElementTypeEntity newType = typeService.create(new StructureElementTypeEntity("Материнская плата"));
        Assertions.assertEquals(5, typeService.getAll(null).size());
        Assertions.assertEquals("Материнская плата", newType.getName());
    }

    @Test
    void createDuplicateTest() {
        final StructureElementTypeEntity duplicateName = new StructureElementTypeEntity("Процессор");
        Assertions.assertThrows(IllegalArgumentException.class, () -> typeService.create(duplicateName));
    }

    @Test
    void createInvalidDataTest() {
        final StructureElementTypeEntity invalidName = new StructureElementTypeEntity("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> typeService.create(invalidName));
    }

    @Test
    void updateTest() {
        final String newName = "Центральный процессор";

        StructureElementTypeEntity updateEntity = new StructureElementTypeEntity(newName);

        final StructureElementTypeEntity updatedEntity = typeService.update(cpuType.getId(), updateEntity);

        Assertions.assertEquals(4, typeService.getAll(null).size());
        Assertions.assertEquals(newName, updatedEntity.getName());

        final StructureElementTypeEntity existingNameUpdate = new StructureElementTypeEntity("Оперативная память");
        Assertions.assertThrows(IllegalArgumentException.class, () -> typeService.update(cpuType.getId(), existingNameUpdate));
    }

    @Test
    void deleteTest() {
        typeService.delete(cpuType.getId());
        Assertions.assertEquals(3, typeService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> typeService.get(cpuType.getId()));
    }

    @Test
    void getAllWithSearchTest() {
        List<StructureElementTypeEntity> results = typeService.getAll("Видеокарта");
        Assertions.assertEquals(1, results.size());

        results = typeService.getAll("память");
        Assertions.assertEquals(1, results.size());

        results = typeService.getAll("е");
        Assertions.assertEquals(3, results.size());

        results = typeService.getAll(null);
        Assertions.assertEquals(4, results.size());
    }
}
