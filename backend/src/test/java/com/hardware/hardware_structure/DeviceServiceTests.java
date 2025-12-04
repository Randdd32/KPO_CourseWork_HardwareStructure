package com.hardware.hardware_structure;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.BuildingEntity;
import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.DeviceEntity;
import com.hardware.hardware_structure.model.entity.DeviceModelEntity;
import com.hardware.hardware_structure.model.entity.DeviceTypeEntity;
import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.model.entity.LocationEntity;
import com.hardware.hardware_structure.model.entity.ManufacturerEntity;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.model.enums.DeviceSortType;
import com.hardware.hardware_structure.model.enums.LocationType;
import com.hardware.hardware_structure.service.entity.BuildingService;
import com.hardware.hardware_structure.service.entity.DepartmentService;
import com.hardware.hardware_structure.service.entity.DeviceModelService;
import com.hardware.hardware_structure.service.entity.DeviceService;
import com.hardware.hardware_structure.service.entity.DeviceTypeService;
import com.hardware.hardware_structure.service.entity.EmployeeService;
import com.hardware.hardware_structure.service.entity.LocationService;
import com.hardware.hardware_structure.service.entity.ManufacturerService;
import com.hardware.hardware_structure.service.entity.PositionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

class DeviceServiceTests extends AbstractIntegrationTest {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceModelService deviceModelService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DeviceTypeService deviceTypeService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private DepartmentService departmentService;

    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private DeviceModelEntity model;
    private LocationEntity location;
    private EmployeeEntity employee;
    private DeviceEntity device1;

    @BeforeEach
    void createData() {
        DeviceTypeEntity type = deviceTypeService.create(new DeviceTypeEntity("Ноутбук"));
        ManufacturerEntity manufacturer = manufacturerService.create(new ManufacturerEntity("Dell"));
        model = deviceModelService.create(new DeviceModelEntity("Latitude 5400", "Рабочий ноутбук", type, manufacturer), List.of());

        BuildingEntity building = buildingService.create(new BuildingEntity("Корпус А", "Адрес"));
        DepartmentEntity dep = departmentService.create(new DepartmentEntity("ИТ"));
        location = locationService.create(new LocationEntity("Офис 101", LocationType.OFFICE, building, dep));

        PositionEntity pos = positionService.create(new PositionEntity("Инженер", null));
        employee = employeeService.create(new EmployeeEntity("Иванов", "Иван", "Иванович", dep, pos));

        Date date1 = createDate(2023, 10, 15);
        Date expiryDate1 = createDate(2026, 10, 15);
        device1 = deviceService.create(new DeviceEntity("SN00001", date1, expiryDate1, 75000.00, true, "Основной рабочий ноутбук", model, location, employee));

        Date date2 = createDate(2022, 5, 1);
        Date expiryDate2 = createDate(2024, 5, 1);
        deviceService.create(new DeviceEntity("SN00002", date2, expiryDate2, 35000.00, false, "Сломан", model, location, null));

        Date date3 = createDate(2024, 1, 1);
        Date expiryDate3 = createDate(2027, 1, 1);
        deviceService.create(new DeviceEntity("SN00003", date3, expiryDate3, 100000.00, true, "Новый ноутбук", model, location, employee));
    }

    @AfterEach
    void removeData() {
        deviceService.getAll().forEach(item -> deviceService.delete(item.getId()));
        employeeService.getAll(null).forEach(item -> employeeService.delete(item.getId()));
        locationService.getAll(null).forEach(item -> locationService.delete(item.getId()));
        buildingService.getAll(null).forEach(item -> buildingService.delete(item.getId()));
        deviceModelService.getAll(null).forEach(item -> deviceModelService.delete(item.getId()));
        deviceTypeService.getAll(null).forEach(item -> deviceTypeService.delete(item.getId()));
        manufacturerService.getAll(null).forEach(item -> manufacturerService.delete(item.getId()));
        departmentService.getAll(null).forEach(item -> departmentService.delete(item.getId()));
        positionService.getAll(null).forEach(item -> positionService.delete(item.getId()));
    }

    @Test
    void getTest() {
        Assertions.assertEquals(3, deviceService.getAll().size());
        Assertions.assertEquals("SN00001", deviceService.get(device1.getId()).getSerialNumber());
        Assertions.assertThrows(NotFoundException.class, () -> deviceService.get(0L));
    }

    @Transactional
    @Test
    void createTest() {
        Date newDate = createDate(2024, 5, 1);
        Date newExpiryDate = createDate(2028, 5, 1);
        DeviceEntity newDevice = new DeviceEntity("SN99999", newDate, newExpiryDate, 55000.00, true, "Тестовое устройство", model, location, employee);

        final DeviceEntity createdDevice = deviceService.create(newDevice);

        Assertions.assertEquals(4, deviceService.getAll().size());
        Assertions.assertEquals("SN99999", createdDevice.getSerialNumber());
    }

    @Test
    void createInvalidDataTest() {
        Date date = createDate(2023, 10, 15);
        Date expiryDate = createDate(2026, 10, 15);
        Date invalidExpiryDate = createDate(2020, 1, 1);

        // Валидация: неуникальный серийный номер
        DeviceEntity duplicateDevice = new DeviceEntity("SN00001", date, expiryDate, 100.00, true, null, model, location, employee);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deviceService.create(duplicateDevice));

        // Валидация: отрицательная цена
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                deviceService.create(new DeviceEntity("SN_PRICE", date, expiryDate, -1.00, true, null, model, location, employee)));

        // Валидация: дата истечения гарантии раньше даты покупки
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                deviceService.create(new DeviceEntity("SN_DATE", date, invalidExpiryDate, 10.00, true, null, model, location, employee)));

        // Валидация: null модель
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                deviceService.create(new DeviceEntity("SN_MODEL", date, expiryDate, 10.00, true, null, null, location, employee)));
    }

    @Transactional
    @Test
    void updateTest() {
        final String newSerialNumber = "UPDATED_SN";
        final double newPrice = 99999.99;

        DeviceEntity updateEntity = new DeviceEntity(newSerialNumber, device1.getPurchaseDate(), device1.getWarrantyExpiryDate(),
                newPrice, false, "Обновлено", model, null, null);

        final DeviceEntity updatedDevice = deviceService.update(device1.getId(), updateEntity);

        Assertions.assertEquals(newSerialNumber, updatedDevice.getSerialNumber());
        Assertions.assertEquals(newPrice, updatedDevice.getPrice());
        Assertions.assertFalse(updatedDevice.isWorking());
        Assertions.assertNull(updatedDevice.getEmployee());
    }

    @Test
    void getBetweenPurchaseDatesTest() {
        Date startDate = createDate(2023, 1, 1);
        Date endDate = createDate(2025, 1, 1);

        List<DeviceEntity> results = deviceService.getBetweenPurchaseDates(startDate, endDate);
        Assertions.assertEquals(2, results.size());

        // Проверка сортировки: OrderByPurchaseDateDesc -> SN00003 должен быть первым
        Assertions.assertEquals("SN00003", results.get(0).getSerialNumber());

        Assertions.assertThrows(IllegalArgumentException.class, () -> deviceService.getBetweenPurchaseDates(endDate, startDate));
    }

    @Test
    void getAllByFiltersTest_IsWorkingAndSorting() {
        // Проверка фильтрации по isWorking=false (должен быть только SN00002)
        Page<DeviceEntity> page = deviceService.getAllByFilters(
                null, null, null, null, null, null,
                false, // isWorking
                null, null, null, null, null, null, null,
                DeviceSortType.PRICE_ASC, // Сортировка по цене
                0, 10
        );

        Assertions.assertEquals(1, page.getTotalElements());
        Assertions.assertEquals("SN00002", page.getContent().get(0).getSerialNumber());

        // Проверка фильтрации по isWorking=true и сортировки по дате покупки (PURCHASE_DATE_ASC)
        page = deviceService.getAllByFilters(
                null, null, null, null, null, null,
                true, // isWorking
                null, null, null, null, null, null, null,
                DeviceSortType.PURCHASE_DATE_ASC,
                0, 10
        );

        Assertions.assertEquals(2, page.getTotalElements());
        // SN00001 (2023-10-15) должен быть первым, SN00003 (2024-01-01) вторым
        Assertions.assertEquals("SN00001", page.getContent().get(0).getSerialNumber());
        Assertions.assertEquals("SN00003", page.getContent().get(1).getSerialNumber());
    }

    @Test
    void deleteTest() {
        deviceService.delete(device1.getId());
        Assertions.assertEquals(2, deviceService.getAll().size());
        Assertions.assertThrows(NotFoundException.class, () -> deviceService.get(device1.getId()));
    }
}
