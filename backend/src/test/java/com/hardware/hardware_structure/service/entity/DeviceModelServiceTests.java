package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.DeviceModelEntity;
import com.hardware.hardware_structure.model.entity.DeviceModelStructureElementEntity;
import com.hardware.hardware_structure.model.entity.DeviceTypeEntity;
import com.hardware.hardware_structure.model.entity.ManufacturerEntity;
import com.hardware.hardware_structure.model.entity.StructureElementModelEntity;
import com.hardware.hardware_structure.model.entity.StructureElementTypeEntity;
import com.hardware.hardware_structure.web.dto.entity.DeviceModelStructureElementIdDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

class DeviceModelServiceTests extends AbstractIntegrationTest {

    @Autowired
    private DeviceModelService deviceModelService;

    @Autowired
    private DeviceTypeService deviceTypeService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private StructureElementModelService structureElementModelService;

    @Autowired
    private StructureElementTypeService structureElementTypeService;

    private DeviceTypeEntity type;
    private ManufacturerEntity manufacturer;
    private DeviceModelEntity model1;

    private StructureElementModelEntity cpuElement;
    private StructureElementModelEntity ramElement;

    @BeforeEach
    void createData() {
        type = deviceTypeService.create(new DeviceTypeEntity("Монитор"));
        manufacturer = manufacturerService.create(new ManufacturerEntity("HP"));

        StructureElementTypeEntity elementTypeCpu = structureElementTypeService.create(new StructureElementTypeEntity("Процессор"));
        StructureElementTypeEntity elementTypeRam = structureElementTypeService.create(new StructureElementTypeEntity("Оперативная память"));

        cpuElement = structureElementModelService.create(new StructureElementModelEntity(
                "Intel i7", "Мощный процессор", elementTypeCpu, manufacturer, 90, 80, 70, 60, 50, 40));

        ramElement = structureElementModelService.create(new StructureElementModelEntity(
                "DDR4 16GB", "Быстрая память", elementTypeRam, manufacturer, 70, 90, 60, 50, 40, 30));

        model1 = deviceModelService.create(new DeviceModelEntity("E243", "Профессиональный монитор", type, manufacturer), List.of());
        deviceModelService.create(new DeviceModelEntity("E273", "Монитор 27 дюймов", type, manufacturer), List.of());
    }

    @Test
    void getTest() {
        Assertions.assertEquals(2, deviceModelService.getAll(null).size());
        Assertions.assertEquals("E243", deviceModelService.get(model1.getId()).getName());

        Assertions.assertThrows(NotFoundException.class, () -> deviceModelService.get(0L));
    }

    @Test
    void getAllWithSearchAndPaginationTest() {
        DeviceTypeEntity printerType = deviceTypeService.create(new DeviceTypeEntity("Принтер"));
        ManufacturerEntity canonManufacturer = manufacturerService.create(new ManufacturerEntity("Canon"));
        deviceModelService.create(new DeviceModelEntity("LaserJet 1", "Цветной принтер", printerType, canonManufacturer), List.of());
        deviceModelService.create(new DeviceModelEntity("DeskJet 2", "Струйный принтер", printerType, canonManufacturer), List.of());
        deviceModelService.create(new DeviceModelEntity("DeskJet 3", "Струйный принтер", printerType, canonManufacturer), List.of());

        // 1. Поиск и пагинация (Страница 0, размер 2, поиск "Jet")
        Page<DeviceModelEntity> page = deviceModelService.getAll("Jet", 0, 2);
        Assertions.assertEquals(3, page.getTotalElements());
        Assertions.assertEquals(2, page.getContent().size());
        Assertions.assertEquals("LaserJet 1", page.getContent().get(0).getName());

        // 2. Страница 1, размер 2, поиск "Jet" (Последний принтер)
        page = deviceModelService.getAll("Jet", 1, 2);
        Assertions.assertEquals(3, page.getTotalElements());
        Assertions.assertEquals(1, page.getContent().size());
        Assertions.assertEquals("DeskJet 3", page.getContent().get(0).getName());

        // 3. Только пагинация (Страница 0, размер 3, всего 5 моделей)
        page = deviceModelService.getAll(null, 0, 3);
        Assertions.assertEquals(5, page.getTotalElements());
        Assertions.assertEquals(3, page.getContent().size());
        Assertions.assertEquals("E243", page.getContent().get(0).getName());

        // 4. Пагинация (Страница 1, размер 3)
        page = deviceModelService.getAll(null, 1, 3);
        Assertions.assertEquals(5, page.getTotalElements());
        Assertions.assertEquals(2, page.getContent().size());
    }

    @Test
    void createTest() {
        final DeviceModelEntity newModel = deviceModelService.create(
                new DeviceModelEntity("Z40c", "Новый Ultrawide", type, manufacturer), List.of());

        Assertions.assertEquals(3, deviceModelService.getAll(null).size());
        Assertions.assertEquals("Z40c", newModel.getName());
    }

    @Test
    void createDuplicateTest() {
        final DeviceModelEntity duplicateModel = new DeviceModelEntity("E243", "Дубликат", type, manufacturer);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deviceModelService.create(duplicateModel, List.of()));
    }

    @Test
    void createWithInvalidDependenciesTest() {
        // Null type
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                deviceModelService.create(new DeviceModelEntity("InvalidModel1", "", null, manufacturer), List.of()));

        // Null manufacturer
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                deviceModelService.create(new DeviceModelEntity("InvalidModel2", "", type, null), List.of()));
    }

    @Test
    void updateTest() {
        DeviceTypeEntity newType = deviceTypeService.create(new DeviceTypeEntity("Моноблок"));
        ManufacturerEntity newManufacturer = manufacturerService.create(new ManufacturerEntity("Lenovo"));

        final String newName = "E243 G4";
        final String newDescription = "Обновленная версия";

        final DeviceModelEntity updatedEntity = deviceModelService.update(
                model1.getId(),
                new DeviceModelEntity(newName, newDescription, newType, newManufacturer),
                List.of()
        );

        Assertions.assertEquals(2, deviceModelService.getAll(null).size());
        Assertions.assertEquals(newName, updatedEntity.getName());
        Assertions.assertEquals(newDescription, updatedEntity.getDescription());
        Assertions.assertEquals(newType.getId(), updatedEntity.getType().getId());
        Assertions.assertEquals(newManufacturer.getId(), updatedEntity.getManufacturer().getId());

        final DeviceModelEntity existingNameUpdate = new DeviceModelEntity("E273", "Любое описание", type, manufacturer);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deviceModelService.update(model1.getId(), existingNameUpdate, List.of()));
    }

    @Test
    void createWithStructureTest() {
        List<DeviceModelStructureElementIdDto> structure = List.of(
                createDto(cpuElement.getId(), 1),
                createDto(ramElement.getId(), 2)
        );

        DeviceModelEntity newModelEntity = new DeviceModelEntity("ServerModel", "Сервер", type, manufacturer);

        final DeviceModelEntity createdModel = deviceModelService.create(newModelEntity, structure);

        Assertions.assertEquals(3, deviceModelService.getAll(null).size());

        DeviceModelEntity fetchedModel = deviceModelService.get(createdModel.getId());

        Assertions.assertEquals(2, fetchedModel.getDeviceModelStructure().size());

        DeviceModelStructureElementEntity ramLink = fetchedModel.getDeviceModelStructure().stream()
                .filter(e -> e.getStructureElement().getId().equals(ramElement.getId()))
                .findFirst().orElseThrow();

        Assertions.assertEquals(2, ramLink.getCount());
    }

    @Test
    void updateStructureTest() {
        DeviceModelEntity startModel = deviceModelService.create(
                new DeviceModelEntity("Laptop_A", "", type, manufacturer),
                List.of(createDto(cpuElement.getId(), 1))
        );

        List<DeviceModelStructureElementIdDto> updateStructure = List.of(
                createDto(ramElement.getId(), 4)
        );

        final DeviceModelEntity updatedModel = deviceModelService.update(
                startModel.getId(),
                new DeviceModelEntity("Laptop_A", "Обновлено", type, manufacturer),
                updateStructure
        );

        DeviceModelEntity fetchedModel = deviceModelService.get(updatedModel.getId());

        Assertions.assertEquals(1, fetchedModel.getDeviceModelStructure().size());

        DeviceModelStructureElementEntity ramLink = fetchedModel.getDeviceModelStructure().stream()
                .filter(e -> e.getStructureElement().getId().equals(ramElement.getId()))
                .findFirst().orElse(null);

        Assertions.assertNotNull(ramLink);
        Assertions.assertEquals(4, ramLink.getCount());

        boolean cpuExists = fetchedModel.getDeviceModelStructure().stream()
                .anyMatch(e -> e.getStructureElement().getId().equals(cpuElement.getId()));
        Assertions.assertFalse(cpuExists);
    }

    @Test
    void deleteTest() {
        deviceModelService.delete(model1.getId());
        Assertions.assertEquals(1, deviceModelService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> deviceModelService.get(model1.getId()));
    }

    @Test
    void metricCalculationTest() {
        // Метрики, заданные в BeforeEach:
        // CPU: WorkEfficiency=90, Reliability=80, EnergyEfficiency=70
        // RAM: WorkEfficiency=70, Reliability=90, EnergyEfficiency=60

        List<DeviceModelStructureElementIdDto> structure = List.of(
                createDto(cpuElement.getId(), 1),
                createDto(ramElement.getId(), 1)
        );

        DeviceModelEntity calculatedModel = deviceModelService.create(
                new DeviceModelEntity("CalculationModel", "", type, manufacturer), structure);

        // 2. Проверка расчета Work Efficiency: (90 + 70) / 2 = 80
        Assertions.assertEquals(80, calculatedModel.getWorkEfficiency());

        // 3. Проверка расчета Reliability: (80 + 90) / 2 = 85
        Assertions.assertEquals(85, calculatedModel.getReliability());

        // 4. Проверка Energy Efficiency: (70 + 60) / 2 = 65
        Assertions.assertEquals(65, calculatedModel.getEnergyEfficiency());

        // 5. Проверка, что модель без структуры возвращает 0
        DeviceModelEntity emptyModel = deviceModelService.create(
                new DeviceModelEntity("EmptyModel", "", type, manufacturer), List.of());
        Assertions.assertEquals(0, emptyModel.getWorkEfficiency());
    }

    private DeviceModelStructureElementIdDto createDto(Long structureElementId, int count) {
        DeviceModelStructureElementIdDto dto = new DeviceModelStructureElementIdDto();
        dto.setStructureElementId(structureElementId);
        dto.setCount(count);
        return dto;
    }
}
