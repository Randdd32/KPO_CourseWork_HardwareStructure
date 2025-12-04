package com.hardware.hardware_structure;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.DeviceTypeEntity;
import com.hardware.hardware_structure.service.entity.DeviceTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class DeviceTypeServiceTests extends AbstractIntegrationTest {

    @Autowired
    private DeviceTypeService deviceTypeService;

    private DeviceTypeEntity mainType;

    @BeforeEach
    void createData() {
        mainType = deviceTypeService.create(new DeviceTypeEntity("Монитор"));
        deviceTypeService.create(new DeviceTypeEntity("Ноутбук"));
        deviceTypeService.create(new DeviceTypeEntity("Принтер"));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(3, deviceTypeService.getAll(null).size());
        Assertions.assertEquals("Монитор", deviceTypeService.get(mainType.getId()).getName());

        Assertions.assertThrows(NotFoundException.class, () -> deviceTypeService.get(0L));
    }

    @Test
    void createTest() {
        final DeviceTypeEntity newType = deviceTypeService.create(new DeviceTypeEntity("Сервер"));
        Assertions.assertEquals(4, deviceTypeService.getAll(null).size());
        Assertions.assertEquals("Сервер", newType.getName());
    }

    @Test
    void createDuplicateTest() {
        final DeviceTypeEntity duplicateType = new DeviceTypeEntity("Монитор");
        Assertions.assertThrows(IllegalArgumentException.class, () -> deviceTypeService.create(duplicateType));
    }

    @Test
    void createInvalidDataTest() {
        // Проверка валидации: пустое имя
        final DeviceTypeEntity invalidName = new DeviceTypeEntity("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> deviceTypeService.create(invalidName));

        // Проверка валидации: null имя
        final DeviceTypeEntity nullName = new DeviceTypeEntity(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deviceTypeService.create(nullName));
    }

    @Test
    void updateTest() {
        final String newName = "Дисплей";

        final DeviceTypeEntity updatedEntity = deviceTypeService.update(
                mainType.getId(),
                new DeviceTypeEntity(newName)
        );

        Assertions.assertEquals(3, deviceTypeService.getAll(null).size());
        Assertions.assertEquals(newName, updatedEntity.getName());

        final DeviceTypeEntity existingNameUpdate = new DeviceTypeEntity("Ноутбук");
        Assertions.assertThrows(IllegalArgumentException.class, () -> deviceTypeService.update(mainType.getId(), existingNameUpdate));
    }

    @Test
    void deleteTest() {
        deviceTypeService.delete(mainType.getId());
        Assertions.assertEquals(2, deviceTypeService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> deviceTypeService.get(mainType.getId()));
    }

    @Test
    void getAllWithSearchTest() {
        List<DeviceTypeEntity> results = deviceTypeService.getAll("Принтер");
        Assertions.assertEquals(1, results.size());

        results = deviceTypeService.getAll("ноу");
        Assertions.assertEquals(1, results.size());

        results = deviceTypeService.getAll("р");
        Assertions.assertEquals(2, results.size());

        results = deviceTypeService.getAll(null);
        Assertions.assertEquals(3, results.size());
    }
}
