package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.ManufacturerEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class ManufacturerServiceTests extends AbstractIntegrationTest {

    @Autowired
    private ManufacturerService manufacturerService;

    private ManufacturerEntity dell;

    @BeforeEach
    void createData() {
        dell = manufacturerService.create(new ManufacturerEntity("Dell"));
        manufacturerService.create(new ManufacturerEntity("HP"));
        manufacturerService.create(new ManufacturerEntity("Cisco"));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(3, manufacturerService.getAll(null).size());
        Assertions.assertEquals("Dell", manufacturerService.get(dell.getId()).getName());

        Assertions.assertThrows(NotFoundException.class, () -> manufacturerService.get(0L));
    }

    @Test
    void getByIdsTest() {
        List<ManufacturerEntity> manufacturers = manufacturerService.getByIds(List.of(dell.getId()));
        Assertions.assertEquals(1, manufacturers.size());
        Assertions.assertEquals("Dell", manufacturers.get(0).getName());

        Assertions.assertTrue(manufacturerService.getByIds(null).isEmpty());
        Assertions.assertTrue(manufacturerService.getByIds(List.of()).isEmpty());
    }

    @Test
    void getAllWithSearchTest() {
        List<ManufacturerEntity> results = manufacturerService.getAll("Cisco");
        Assertions.assertEquals(1, results.size());

        results = manufacturerService.getAll("hP");
        Assertions.assertEquals(1, results.size());

        results = manufacturerService.getAll("l");
        Assertions.assertEquals(1, results.size());

        results = manufacturerService.getAll(null);
        Assertions.assertEquals(3, results.size());
    }

    @Test
    void createTest() {
        final ManufacturerEntity newManufacturer = manufacturerService.create(new ManufacturerEntity("Lenovo"));
        Assertions.assertEquals(4, manufacturerService.getAll(null).size());
        Assertions.assertEquals("Lenovo", newManufacturer.getName());
    }

    @Test
    void createDuplicateTest() {
        final ManufacturerEntity duplicateManufacturer = new ManufacturerEntity("Dell");
        Assertions.assertThrows(IllegalArgumentException.class, () -> manufacturerService.create(duplicateManufacturer));
    }

    @Test
    void createInvalidDataTest() {
        final ManufacturerEntity invalidName = new ManufacturerEntity("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> manufacturerService.create(invalidName));
    }

    @Test
    void updateTest() {
        final String newName = "Dell Technologies";

        final ManufacturerEntity updatedEntity = manufacturerService.update(
                dell.getId(),
                new ManufacturerEntity(newName)
        );

        Assertions.assertEquals(3, manufacturerService.getAll(null).size());
        Assertions.assertEquals(newName, updatedEntity.getName());

        final ManufacturerEntity existingNameUpdate = new ManufacturerEntity("HP");
        Assertions.assertThrows(IllegalArgumentException.class, () -> manufacturerService.update(dell.getId(), existingNameUpdate));
    }

    @Test
    void deleteTest() {
        manufacturerService.delete(dell.getId());
        Assertions.assertEquals(2, manufacturerService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> manufacturerService.get(dell.getId()));
    }
}
