package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.ManufacturerEntity;
import com.hardware.hardware_structure.model.entity.StructureElementModelEntity;
import com.hardware.hardware_structure.model.entity.StructureElementTypeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

class StructureElementModelServiceTests extends AbstractIntegrationTest {

    @Autowired
    private StructureElementModelService modelService;

    @Autowired
    private StructureElementTypeService typeService;

    @Autowired
    private ManufacturerService manufacturerService;

    private StructureElementTypeEntity typeCpu;
    private StructureElementTypeEntity typeRam;
    private ManufacturerEntity manufacturerIntel;
    private ManufacturerEntity manufacturerAMD;
    private StructureElementModelEntity cpuI7;
    private StructureElementModelEntity cpuI5;

    @BeforeEach
    void createData() {
        typeCpu = typeService.create(new StructureElementTypeEntity("Процессор"));
        typeRam = typeService.create(new StructureElementTypeEntity("Оперативная память"));

        manufacturerIntel = manufacturerService.create(new ManufacturerEntity("Intel"));
        manufacturerAMD = manufacturerService.create(new ManufacturerEntity("AMD"));

        cpuI7 = createModel(
                "Intel Core i7-11700", "Мощный десктопный процессор", typeCpu, manufacturerIntel,
                90, 80, 70, 60, 50, 40);

        cpuI5 = createModel(
                "Intel Core i5-11400", "Средний десктопный процессор", typeCpu, manufacturerIntel,
                80, 90, 70, 60, 50, 40);

        createModel("AMD Ryzen 7 5800X", "Мощный процессор AMD", typeCpu, manufacturerAMD,
                95, 85, 80, 65, 55, 45);
    }

    @Test
    void getTest() {
        Assertions.assertEquals(3, modelService.getAll(null).size());
        Assertions.assertEquals("Intel Core i7-11700", modelService.get(cpuI7.getId()).getName());

        Assertions.assertThrows(NotFoundException.class, () -> modelService.get(0L));
    }

    @Test
    void getAllWithSearchAndPaginationTest() {
        // Добавляем еще 3 RAM-модуля
        createModel("Kingston DDR4 16GB", "Быстрая память", typeRam, manufacturerIntel, 80, 80, 90, 70, 60, 50);
        createModel("Crucial DDR4 8GB", "Бюджетная память", typeRam, manufacturerAMD, 70, 90, 70, 60, 50, 40);
        createModel("Corsair Vengeance", "Премиум память", typeRam, manufacturerIntel, 90, 70, 95, 80, 70, 60);

        // 1. Поиск по имени "Intel": найдено только 2 модели
        Page<StructureElementModelEntity> page = modelService.getAll("Intel", 0, 2);
        Assertions.assertEquals(2, page.getTotalElements());
        Assertions.assertEquals(2, page.getContent().size());
        Assertions.assertEquals("Intel Core i7-11700", page.getContent().get(0).getName());
        Assertions.assertEquals("Intel Core i5-11400", page.getContent().get(1).getName());

        // 2. Вторая страница — пустая
        page = modelService.getAll("Intel", 1, 2);
        Assertions.assertEquals(2, page.getTotalElements());
        Assertions.assertTrue(page.getContent().isEmpty());

        // 3. Только пагинация (Страница 0, размер 4, всего 6 моделей)
        page = modelService.getAll(null, 0, 4);
        Assertions.assertEquals(6, page.getTotalElements());
        Assertions.assertEquals(4, page.getContent().size());
        Assertions.assertEquals(cpuI7.getName(), page.getContent().get(0).getName());

        // 4. Пагинация (Страница 1, размер 4)
        page = modelService.getAll(null, 1, 4);
        Assertions.assertEquals(6, page.getTotalElements());
        Assertions.assertEquals(2, page.getContent().size());
        Assertions.assertEquals("Crucial DDR4 8GB", page.getContent().get(0).getName());
    }

    @Test
    void createTest() {
        final StructureElementModelEntity newModel = createModel(
                "AMD Ryzen 5 5600", "Хороший середняк", typeCpu, manufacturerAMD,
                85, 85, 75, 60, 50, 40);

        Assertions.assertEquals(4, modelService.getAll(null).size());
        Assertions.assertEquals("AMD Ryzen 5 5600", newModel.getName());
        Assertions.assertEquals(85, newModel.getWorkEfficiency());
    }

    @Test
    void createDuplicateTest() {
        final StructureElementModelEntity duplicateName = new StructureElementModelEntity(
                "Intel Core i7-11700", "Дубликат", typeCpu, manufacturerIntel,
                10, 10, 10, 10, 10, 10);
        Assertions.assertThrows(IllegalArgumentException.class, () -> modelService.create(duplicateName));
    }

    @Test
    void updateTest() {
        StructureElementTypeEntity newType = typeService.create(new StructureElementTypeEntity("SSD"));
        ManufacturerEntity newManufacturer = manufacturerService.create(new ManufacturerEntity("Samsung"));

        final String newName = "Intel Core i7-11700K";
        final String newDescription = "Обновленная, разблокированная версия";
        final int newWorkEfficiency = 99;
        final int newReliability = 98;
        final int newEnergyEfficiency = 97;
        final int newUserFriendliness = 96;
        final int newDurability = 95;
        final int newAestheticQualities = 94;

        StructureElementModelEntity updateEntity = new StructureElementModelEntity(
                newName, newDescription, newType, newManufacturer,
                newWorkEfficiency, newReliability, newEnergyEfficiency,
                newUserFriendliness, newDurability, newAestheticQualities);

        final StructureElementModelEntity updatedEntity = modelService.update(cpuI7.getId(), updateEntity);

        Assertions.assertEquals(3, modelService.getAll(null).size());
        Assertions.assertEquals(newName, updatedEntity.getName());
        Assertions.assertEquals(newDescription, updatedEntity.getDescription());
        Assertions.assertEquals(newType.getId(), updatedEntity.getType().getId());
        Assertions.assertEquals(newManufacturer.getId(), updatedEntity.getManufacturer().getId());

        Assertions.assertEquals(newWorkEfficiency, updatedEntity.getWorkEfficiency());
        Assertions.assertEquals(newReliability, updatedEntity.getReliability());
        Assertions.assertEquals(newEnergyEfficiency, updatedEntity.getEnergyEfficiency());
        Assertions.assertEquals(newUserFriendliness, updatedEntity.getUserFriendliness());
        Assertions.assertEquals(newDurability, updatedEntity.getDurability());
        Assertions.assertEquals(newAestheticQualities, updatedEntity.getAestheticQualities());

        final StructureElementModelEntity existingNameUpdate = new StructureElementModelEntity(
                cpuI5.getName(), "Любое описание", typeCpu, manufacturerIntel,
                10, 10, 10, 10, 10, 10);
        Assertions.assertThrows(IllegalArgumentException.class, () -> modelService.update(cpuI7.getId(), existingNameUpdate));
    }

    @Test
    void deleteTest() {
        modelService.delete(cpuI7.getId());
        Assertions.assertEquals(2, modelService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> modelService.get(cpuI7.getId()));
    }

    private StructureElementModelEntity createModel(String name, String description, StructureElementTypeEntity type,
                                                    ManufacturerEntity manufacturer, int we, int rel, int ee, int uf, int dur, int ae) {
        return modelService.create(new StructureElementModelEntity(
                name, description, type, manufacturer, we, rel, ee, uf, dur, ae
        ));
    }
}
