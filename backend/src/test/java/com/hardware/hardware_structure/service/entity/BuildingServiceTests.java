package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.BuildingEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class BuildingServiceTests extends AbstractIntegrationTest {

    @Autowired
    private BuildingService buildingService;

    private BuildingEntity mainBuilding;

    @BeforeEach
    void createData() {
        mainBuilding = buildingService.create(new BuildingEntity("Главное здание", "г.Москва, ул.Ленина, д.1"));
        buildingService.create(new BuildingEntity("Корпус B", "г. Москва, ул. Кирова, д.21"));
        buildingService.create(new BuildingEntity("Корпус C", "г. Москва, ул. Гагарина, д.15"));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(3, buildingService.getAll(null).size());
        Assertions.assertEquals("Главное здание", buildingService.get(mainBuilding.getId()).getName());

        Assertions.assertThrows(NotFoundException.class, () -> buildingService.get(0L));
    }

    @Test
    void getAllWithSearchTest() {
        List<BuildingEntity> results = buildingService.getAll("Корпус B");
        Assertions.assertEquals(1, results.size());

        results = buildingService.getAll("корпус");
        Assertions.assertEquals(2, results.size());

        results = buildingService.getAll(null);
        Assertions.assertEquals(3, results.size());
    }

    @Test
    void getByIdsTest() {
        List<BuildingEntity> buildings = buildingService.getByIds(List.of(mainBuilding.getId()));
        Assertions.assertEquals(1, buildings.size());
        Assertions.assertEquals("Главное здание", buildings.get(0).getName());

        Assertions.assertTrue(buildingService.getByIds(null).isEmpty());
        Assertions.assertTrue(buildingService.getByIds(List.of()).isEmpty());
    }

    @Test
    void createTest() {
        final BuildingEntity newBuilding = buildingService.create(new BuildingEntity("Склад", "г. Москва, ул. Складская, д.5"));
        Assertions.assertEquals(4, buildingService.getAll(null).size());
        Assertions.assertEquals("Склад", newBuilding.getName());
    }

    @Test
    void createDuplicateTest() {
        final BuildingEntity duplicateBuilding = new BuildingEntity("Главное здание", "Другой адрес");
        Assertions.assertThrows(IllegalArgumentException.class, () -> buildingService.create(duplicateBuilding));
    }

    @Test
    void createInvalidDataTest() {
        final BuildingEntity invalidName = new BuildingEntity("", "Адрес");
        Assertions.assertThrows(IllegalArgumentException.class, () -> buildingService.create(invalidName));

        final BuildingEntity invalidAddress = new BuildingEntity("Новый", " ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> buildingService.create(invalidAddress));
    }

    @Test
    void updateTest() {
        final String newName = "Новое главное здание";
        final String newAddress = "Новый адрес";

        final BuildingEntity updatedEntity = buildingService.update(
                mainBuilding.getId(),
                new BuildingEntity(newName, newAddress)
        );

        Assertions.assertEquals(3, buildingService.getAll(null).size());
        Assertions.assertEquals(newName, updatedEntity.getName());
        Assertions.assertEquals(newAddress, updatedEntity.getAddress());

        final BuildingEntity existingNameUpdate = new BuildingEntity("Корпус B", "Любой адрес");
        Assertions.assertThrows(IllegalArgumentException.class, () -> buildingService.update(mainBuilding.getId(), existingNameUpdate));
    }

    @Test
    void deleteTest() {
        buildingService.delete(mainBuilding.getId());
        Assertions.assertEquals(2, buildingService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> buildingService.get(mainBuilding.getId()));
    }
}
