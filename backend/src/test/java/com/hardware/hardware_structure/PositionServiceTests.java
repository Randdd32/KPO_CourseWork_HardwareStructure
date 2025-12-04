package com.hardware.hardware_structure;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.service.entity.PositionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

class PositionServiceTests extends AbstractIntegrationTest {

    @Autowired
    private PositionService positionService;

    private PositionEntity engineer;

    @BeforeEach
    void createData() {
        engineer = positionService.create(new PositionEntity("Инженер", "Отвечает за инфраструктуру"));
        positionService.create(new PositionEntity("Менеджер", "Руководит проектами"));
        positionService.create(new PositionEntity("Директор", "Высшее руководство"));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(3, positionService.getAll(null).size());
        Assertions.assertEquals("Инженер", positionService.get(engineer.getId()).getName());

        Assertions.assertThrows(NotFoundException.class, () -> positionService.get(0L));
    }

    @Test
    void createTest() {
        final PositionEntity newPosition = positionService.create(new PositionEntity("Бухгалтер", "Финансовый учет"));
        Assertions.assertEquals(4, positionService.getAll(null).size());
        Assertions.assertEquals("Бухгалтер", newPosition.getName());
        Assertions.assertEquals("Финансовый учет", newPosition.getDescription());
    }

    @Test
    void createDuplicateTest() {
        final PositionEntity duplicatePosition = new PositionEntity("Инженер", "Любое описание");
        Assertions.assertThrows(IllegalArgumentException.class, () -> positionService.create(duplicatePosition));
    }

    @Test
    void createInvalidDataTest() {
        final PositionEntity invalidName = new PositionEntity("", "Описание");
        Assertions.assertThrows(IllegalArgumentException.class, () -> positionService.create(invalidName));
    }

    @Test
    void updateTest() {
        final String newName = "Старший инженер";
        final String newDescription = "Отвечает за всю инфраструктуру";

        PositionEntity updateEntity = new PositionEntity(newName, newDescription);

        final PositionEntity updatedEntity = positionService.update(engineer.getId(), updateEntity);

        Assertions.assertEquals(3, positionService.getAll(null).size());
        Assertions.assertEquals(newName, updatedEntity.getName());
        Assertions.assertEquals(newDescription, updatedEntity.getDescription());

        final String emptyDescriptionName = "Младший инженер";
        final PositionEntity updatedEmptyDesc = positionService.update(engineer.getId(), new PositionEntity(emptyDescriptionName, null));
        Assertions.assertEquals(emptyDescriptionName, updatedEmptyDesc.getName());
        Assertions.assertNull(updatedEmptyDesc.getDescription());

        final PositionEntity existingNameUpdate = new PositionEntity("Менеджер", "Любое описание");
        Assertions.assertThrows(IllegalArgumentException.class, () -> positionService.update(engineer.getId(), existingNameUpdate));
    }

    @Test
    void deleteTest() {
        positionService.delete(engineer.getId());
        Assertions.assertEquals(2, positionService.getAll(null).size());
        Assertions.assertThrows(NotFoundException.class, () -> positionService.get(engineer.getId()));
    }

    @Test
    void getAllWithSearchAndPaginationTest() {
        positionService.create(new PositionEntity("Программист", "Разработка приложений"));
        positionService.create(new PositionEntity("Секретарь", "Административные задачи"));
        positionService.create(new PositionEntity("Охранник", "Безопасность"));

        // Поиск по части названия "ер" (ИнженЕР, МенеджЕР). Всего 2 должности.

        // 1. Поиск и пагинация (Страница 0, размер 1, поиск "ер").
        Page<PositionEntity> page = positionService.getAll("ер", 0, 1);
        Assertions.assertEquals(2, page.getTotalElements());
        Assertions.assertEquals(1, page.getContent().size());
        // Сортировка по ID ASC: Инженер должен быть первым
        Assertions.assertEquals("Инженер", page.getContent().get(0).getName());

        // 2. Страница 1, размер 1, поиск "ер". Должность: "Менеджер".
        page = positionService.getAll("ер", 1, 1);
        Assertions.assertEquals(2, page.getTotalElements());
        Assertions.assertEquals(1, page.getContent().size());
        Assertions.assertEquals("Менеджер", page.getContent().get(0).getName());

        // 3. Только пагинация (Страница 0, размер 4, всего 6 должностей)
        page = positionService.getAll(null, 0, 4);
        Assertions.assertEquals(6, page.getTotalElements());
        Assertions.assertEquals(4, page.getContent().size());
        Assertions.assertEquals("Инженер", page.getContent().get(0).getName());

        // 4. Пагинация (Страница 1, размер 4)
        page = positionService.getAll(null, 1, 4);
        Assertions.assertEquals(6, page.getTotalElements());
        Assertions.assertEquals(2, page.getContent().size());
        Assertions.assertEquals("Секретарь", page.getContent().get(0).getName());
    }
}
