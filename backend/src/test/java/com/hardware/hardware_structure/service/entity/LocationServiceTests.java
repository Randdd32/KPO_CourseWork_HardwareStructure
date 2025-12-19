package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.BuildingEntity;
import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.LocationEntity;
import com.hardware.hardware_structure.model.enums.LocationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

class LocationServiceTests extends AbstractIntegrationTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private DepartmentService departmentService;

    private BuildingEntity buildingA;
    private BuildingEntity buildingB;
    private DepartmentEntity itDepartment;
    private DepartmentEntity hrDepartment;
    private LocationEntity office101;

    @BeforeEach
    void createData() {
        buildingA = buildingService.create(new BuildingEntity("Корпус А", "ул. Первая"));
        buildingB = buildingService.create(new BuildingEntity("Корпус Б", "ул. Вторая"));
        itDepartment = departmentService.create(new DepartmentEntity("ИТ"));
        hrDepartment = departmentService.create(new DepartmentEntity("HR"));

        office101 = locationService.create(new LocationEntity("Офис 101", LocationType.OFFICE, buildingA, itDepartment));
        locationService.create(new LocationEntity("Серверная 205", LocationType.SERVER_ROOM, buildingA, itDepartment));
        locationService.create(new LocationEntity("Склад 1", LocationType.STORAGE, buildingB, null));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(3, locationService.getAll(null).size());
        Assertions.assertEquals("Офис 101", locationService.get(office101.getId()).getName());

        Assertions.assertThrows(NotFoundException.class, () -> locationService.get(0L));
    }

    @Test
    void getByIdsTest() {
        List<LocationEntity> locations = locationService.getByIds(List.of(office101.getId()));
        Assertions.assertEquals(1, locations.size());
        Assertions.assertEquals("Офис 101", locations.get(0).getName());

        Assertions.assertTrue(locationService.getByIds(null).isEmpty());
        Assertions.assertTrue(locationService.getByIds(List.of()).isEmpty());
    }

    @Test
    void getAllWithSearchAndPaginationTest() {
        locationService.create(new LocationEntity("Офис 300", LocationType.OFFICE, buildingA, hrDepartment));
        locationService.create(new LocationEntity("Кабинет 301", LocationType.OFFICE, buildingA, hrDepartment));
        locationService.create(new LocationEntity("Склад 2", LocationType.STORAGE, buildingB, null));

        // 1. Поиск и пагинация (Страница 0, размер 1, поиск "Склад"). Всего 2 склада.
        Page<LocationEntity> page = locationService.getAll("Склад", 0, 1);
        Assertions.assertEquals(2, page.getTotalElements());
        Assertions.assertEquals(1, page.getContent().size());
        Assertions.assertEquals("Склад 1", page.getContent().get(0).getName()); // Проверка сортировки по ID

        // 2. Страница 1, размер 1, поиск "Склад"
        page = locationService.getAll("Склад", 1, 1);
        Assertions.assertEquals(2, page.getTotalElements());
        Assertions.assertEquals(1, page.getContent().size());
        Assertions.assertEquals("Склад 2", page.getContent().get(0).getName());

        // 3. Только пагинация (Страница 0, размер 3, всего 6 локаций)
        page = locationService.getAll(null, 0, 3);
        Assertions.assertEquals(6, page.getTotalElements());
        Assertions.assertEquals(3, page.getContent().size());
        Assertions.assertEquals("Офис 101", page.getContent().get(0).getName());

        // 4. Пагинация (Страница 1, размер 3)
        page = locationService.getAll(null, 1, 3);
        Assertions.assertEquals(6, page.getTotalElements());
        Assertions.assertEquals(3, page.getContent().size());
        Assertions.assertEquals("Офис 300", page.getContent().get(0).getName());
    }

    @Test
    void createTest() {
        final LocationEntity newLocation = locationService.create(new LocationEntity("Кафетерий", LocationType.CAFETERIA, buildingB, hrDepartment));
        Assertions.assertEquals(4, locationService.getAll(null).size());
        Assertions.assertEquals("Кафетерий", newLocation.getName());
        Assertions.assertEquals(LocationType.CAFETERIA, newLocation.getType());
        Assertions.assertEquals(buildingB.getId(), newLocation.getBuilding().getId());
        Assertions.assertEquals(hrDepartment.getId(), newLocation.getDepartment().getId());
    }

    @Test
    void createDuplicateTest() {
        final LocationEntity duplicate = new LocationEntity("Офис 101", LocationType.OFFICE, buildingB, hrDepartment);
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.create(duplicate));
    }

    @Test
    void createInvalidDataTest() {
        // Null Building
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                locationService.create(new LocationEntity("Тест", LocationType.OFFICE, null, itDepartment)));

        // Invalid Name
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                locationService.create(new LocationEntity("", LocationType.OFFICE, buildingA, itDepartment)));
    }

    @Test
    void updateTest() {
        final String newName = "Кабинет директора 102";
        final LocationType newType = LocationType.OFFICE;

        LocationEntity updateEntity = new LocationEntity(newName, newType, buildingB, hrDepartment);

        final LocationEntity updatedEntity = locationService.update(office101.getId(), updateEntity);

        Assertions.assertEquals(3, locationService.getAll(null).size());
        Assertions.assertEquals(newName, updatedEntity.getName());
        Assertions.assertEquals(newType, updatedEntity.getType());
        Assertions.assertEquals(buildingB.getId(), updatedEntity.getBuilding().getId());
        Assertions.assertEquals(hrDepartment.getId(), updatedEntity.getDepartment().getId());

        // Проверка обновления на null department (допустимо)
        LocationEntity updateToNull = new LocationEntity("Test", LocationType.STORAGE, buildingA, null);
        final LocationEntity updatedToNull = locationService.update(office101.getId(), updateToNull);
        Assertions.assertNull(updatedToNull.getDepartment());

        // Проверка нарушения уникальности имени
        final LocationEntity existingNameUpdate = new LocationEntity("Серверная 205", LocationType.OFFICE, buildingA, itDepartment);
        Assertions.assertThrows(IllegalArgumentException.class, () -> locationService.update(office101.getId(), existingNameUpdate));
    }

    @Test
    void deleteTest() {
        locationService.delete(office101.getId());
        Assertions.assertEquals(2, locationService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> locationService.get(office101.getId()));
    }
}
