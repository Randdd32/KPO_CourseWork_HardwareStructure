package com.hardware.hardware_structure.core.setup;

import com.hardware.hardware_structure.core.configuration.AppConfigurationProperties;
import com.hardware.hardware_structure.core.log.Loggable;
import com.hardware.hardware_structure.core.utility.Formatter;
import com.hardware.hardware_structure.model.entity.BuildingEntity;
import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.DeviceEntity;
import com.hardware.hardware_structure.model.entity.DeviceModelEntity;
import com.hardware.hardware_structure.model.entity.DeviceTypeEntity;
import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.model.entity.LocationEntity;
import com.hardware.hardware_structure.model.entity.ManufacturerEntity;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.model.entity.StructureElementModelEntity;
import com.hardware.hardware_structure.model.entity.StructureElementTypeEntity;
import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.model.enums.LocationType;
import com.hardware.hardware_structure.model.enums.UserRole;
import com.hardware.hardware_structure.service.entity.BuildingService;
import com.hardware.hardware_structure.service.entity.DepartmentService;
import com.hardware.hardware_structure.service.entity.DeviceModelService;
import com.hardware.hardware_structure.service.entity.DeviceService;
import com.hardware.hardware_structure.service.entity.DeviceTypeService;
import com.hardware.hardware_structure.service.entity.EmployeeService;
import com.hardware.hardware_structure.service.entity.LocationService;
import com.hardware.hardware_structure.service.entity.ManufacturerService;
import com.hardware.hardware_structure.service.entity.PositionService;
import com.hardware.hardware_structure.service.entity.StructureElementModelService;
import com.hardware.hardware_structure.service.entity.StructureElementTypeService;
import com.hardware.hardware_structure.service.entity.UserService;
import com.hardware.hardware_structure.web.dto.entity.DeviceModelStructureElementIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityInitializer {
    private final BuildingService buildingService;
    private final DepartmentService departmentService;
    private final DeviceModelService deviceModelService;
    private final DeviceService deviceService;
    private final DeviceTypeService deviceTypeService;
    private final EmployeeService employeeService;
    private final LocationService locationService;
    private final ManufacturerService manufacturerService;
    private final PositionService positionService;
    private final StructureElementModelService structureElementModelService;
    private final StructureElementTypeService structureElementTypeService;
    private final UserService userService;
    private final AppConfigurationProperties appConfigurationProperties;

    @Loggable
    @Transactional
    public void initializeAll() {
        List<ManufacturerEntity> manufacturers = createManufacturers();
        List<DeviceTypeEntity> deviceTypes = createDeviceTypes();
        List<StructureElementTypeEntity> structureElementTypes = createStructureElementTypes();
        List<StructureElementModelEntity> structureElementModels = createStructureElementModels(structureElementTypes, manufacturers);
        List<DeviceModelEntity> deviceModels = createDeviceModels(deviceTypes, manufacturers, structureElementModels);
        List<BuildingEntity> buildings = createBuildings();
        List<PositionEntity> positions = createPositions();
        List<DepartmentEntity> departments = createDepartments(positions);
        List<EmployeeEntity> employees = createEmployees(positions, departments);
        List<LocationEntity> locations = createLocations(buildings, departments, employees);
        createDevices(deviceModels, locations, employees);
        createUser(employees.get(6));
    }

    @Loggable
    private List<ManufacturerEntity> createManufacturers() {
        List<ManufacturerEntity> manufacturers = new ArrayList<>();
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("HP")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Dell")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Lenovo")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Apple")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Samsung")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Acer")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Asus")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Intel")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("AMD")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Cisco")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("MSI")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Xerox")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Brother")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("TP-Link")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Huawei")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Cooler Master")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Kingston")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Seagate")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Western Digital")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Corsair")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("NVIDIA")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Gigabyte")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Noctua")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("BenQ")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("DeepCool")));
        manufacturers.add(manufacturerService.create(new ManufacturerEntity("Dexp")));
        return manufacturers;
    }

    @Loggable
    private List<DeviceTypeEntity> createDeviceTypes() {
        List<DeviceTypeEntity> deviceTypes = new ArrayList<>();
        deviceTypes.add(deviceTypeService.create(new DeviceTypeEntity("Сервер")));
        deviceTypes.add(deviceTypeService.create(new DeviceTypeEntity("Персональный компьютер")));
        deviceTypes.add(deviceTypeService.create(new DeviceTypeEntity("Ноутбук")));
        deviceTypes.add(deviceTypeService.create(new DeviceTypeEntity("Монитор")));
        deviceTypes.add(deviceTypeService.create(new DeviceTypeEntity("Принтер")));
        deviceTypes.add(deviceTypeService.create(new DeviceTypeEntity("Роутер")));
        deviceTypes.add(deviceTypeService.create(new DeviceTypeEntity("Проектор")));
        deviceTypes.add(deviceTypeService.create(new DeviceTypeEntity("Телефон")));
        return deviceTypes;
    }

    @Loggable
    private List<StructureElementTypeEntity> createStructureElementTypes() {
        List<StructureElementTypeEntity> structureElementTypes = new ArrayList<>();
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Корпус")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Процессор")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Кулер для процессора")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Материнская плата")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Оперативная память")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Жесткий диск")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Твердотельный накопитель")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Видеоадаптер")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("DVD привод")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Блок питания")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Матрица дисплея")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Подставка для монитора")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Печатающая головка")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Лазерный модуль")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Блок барабана")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Картридж")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Антенны")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Плата роутера")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Лампа проектора")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Объектив")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Трубка телефона")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Дисплей телефона")));
        structureElementTypes.add(structureElementTypeService.create(new StructureElementTypeEntity("Гарнитура для громкой связи")));
        return structureElementTypes;
    }

    @Loggable
    private List<StructureElementModelEntity> createStructureElementModels(List<StructureElementTypeEntity> types, List<ManufacturerEntity> manufacturers) {
        List<StructureElementModelEntity> structureElements = new ArrayList<>();
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Intel Core i5-10400",
                "", types.get(1), manufacturers.get(7), 70, 85, 75, 80, 90, 60)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("AMD Ryzen 5 3600",
                "6-ядерный процессор для игр и работы", types.get(1), manufacturers.get(8), 85, 80, 70, 75, 85, 65)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Apple M1 Pro",
                "Процессор для MacBook", types.get(1), manufacturers.get(3), 90, 95, 85, 90, 95, 85)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Intel Core i7-10700K",
                "8-ядерный мощный процессор", types.get(1), manufacturers.get(7), 95, 90, 80, 85, 90, 75)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("ASUS Prime B460M-A",
                "", types.get(3), manufacturers.get(6), 65, 80, 70, 75, 85, 60)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Gigabyte B550 Aorus Elite",
                "Игровая материнская плата", types.get(3), manufacturers.get(21), 85, 85, 70, 80, 90, 75)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Apple MacBook Pro Logic Board",
                "Материнская плата для MacBook", types.get(3), manufacturers.get(3), 90, 95, 85, 90, 95, 85)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Corsair Vengeance LPX 16GB",
                "", types.get(4), manufacturers.get(19), 80, 85, 70, 75, 90, 60)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Kingston HyperX Fury 8GB",
                "", types.get(4), manufacturers.get(16), 75, 80, 65, 70, 85, 55)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Samsung 970 EVO Plus 500GB",
                "SSD для высокой производительности", types.get(6), manufacturers.get(4), 85, 90, 80, 80, 85, 70)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Western Digital Blue 1TB HDD",
                "Жесткий диск для офисных задач", types.get(5), manufacturers.get(18), 65, 80, 60, 70, 85, 50)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Corsair CV450",
                "450 Вт блок питания", types.get(9), manufacturers.get(19), 75, 85, 80, 80, 85, 60)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Cooler Master 600W",
                "600 Вт блок питания", types.get(9), manufacturers.get(15), 85, 90, 75, 80, 85, 65)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("DEXP DC-101B",
                "Недорогой корпус для офисных ПК", types.get(0), manufacturers.get(25), 60, 75, 65, 70, 80, 55)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Cooler Master Silencio S600",
                "Корпус для ПК средней ценовой категории", types.get(0), manufacturers.get(15), 75, 85, 70, 75, 90, 75)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Apple MacBook Pro Case",
                "Корпус для MacBook", types.get(0), manufacturers.get(3), 90, 95, 85, 90, 95, 85)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("DEEPCOOL AG300",
                "Недорогой, но эффективный кулер для процессора", types.get(2), manufacturers.get(24), 70, 80, 70, 65, 75, 60)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Noctua NH-D15",
                "Кулер для процессора премиум-сегмента", types.get(2), manufacturers.get(22), 85, 90, 75, 70, 85, 70)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("NVIDIA RTX 3070",
                "Игровая видеокарта", types.get(7), manufacturers.get(20), 95, 90, 70, 85, 90, 80)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Dell UltraSharp 27 Display",
                "Матрица дисплея Dell UltraSharp 27", types.get(10), manufacturers.get(1), 85, 90, 85, 80, 85, 80)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Samsung Odyssey G7 Display",
                "Матрица дисплея Samsung Odyssey G7", types.get(10), manufacturers.get(4), 90, 85, 80, 85, 90, 85)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Dell Monitor Stand",
                "Подставка для мониторов Dell", types.get(11), manufacturers.get(1), 80, 85, 75, 70, 85, 75)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Samsung Monitor Stand",
                "Подставка для мониторов Samsung", types.get(11), manufacturers.get(4), 85, 90, 80, 80, 90, 80)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Xerox Drum Unit",
                "Блок барабана Xerox", types.get(14), manufacturers.get(11), 75, 80, 70, 65, 85, 60)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Brother Toner Cartridge",
                "Картридж Brother", types.get(15), manufacturers.get(12), 70, 75, 65, 70, 80, 55)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("TP-Link Archer Antennas",
                "Антенны для TP-Link Archer", types.get(16), manufacturers.get(13), 70, 75, 65, 70, 80, 60)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("TP-Link Router Board",
                "Плата роутера TP-Link", types.get(17), manufacturers.get(13), 75, 80, 70, 75, 85, 65)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("BenQ Projector Lamp",
                "Лампа проектора BenQ", types.get(18), manufacturers.get(23), 80, 85, 70, 75, 85, 70)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("BenQ Projector Lens",
                "Объектив проектора BenQ", types.get(19), manufacturers.get(23), 80, 85, 75, 70, 85, 75)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Cisco IP Phone Handset",
                "Трубка телефона Cisco", types.get(20), manufacturers.get(9), 75, 80, 65, 70, 80, 60)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Cisco IP Phone Display",
                "Дисплей телефона Cisco", types.get(21), manufacturers.get(9), 75, 80, 70, 75, 85, 65)));
        structureElements.add(structureElementModelService.create(new StructureElementModelEntity("Cisco Speakerphone Unit",
                "Гарнитура для громкой связи Cisco", types.get(22), manufacturers.get(9), 80, 85, 75, 70, 85, 70)));
        return structureElements;
    }

    @Loggable
    protected List<DeviceModelEntity> createDeviceModels(List<DeviceTypeEntity> types, List<ManufacturerEntity> manufacturers,
                                                         List<StructureElementModelEntity> structureElements) {
        List<DeviceModelEntity> deviceModels = new ArrayList<>();

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("HP Pavilion 15",
                "Ноутбук среднего уровня с хорошей производительностью для повседневных задач",
                types.get(2), manufacturers.get(0)), List.of(
                createDto(structureElements.get(0).getId(), 1),
                createDto(structureElements.get(4).getId(), 1),
                createDto(structureElements.get(8).getId(), 1),
                createDto(structureElements.get(10).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("Lenovo ThinkPad X1",
                "Премиум ультрабук с высокой производительностью и долговечностью", types.get(2), manufacturers.get(2)), List.of(
                createDto(structureElements.get(1).getId(), 1),
                createDto(structureElements.get(5).getId(), 1),
                createDto(structureElements.get(8).getId(), 2)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("Dell XPS 13",
                "Компактный и мощный ультрабук", types.get(2), manufacturers.get(1)), List.of(
                createDto(structureElements.get(1).getId(), 1),
                createDto(structureElements.get(5).getId(), 1),
                createDto(structureElements.get(9).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("Apple MacBook Pro 16",
                "Профессиональный ноутбук для креативных задач с мощным процессором и графикой", types.get(2), manufacturers.get(3)), List.of(
                createDto(structureElements.get(2).getId(), 1),
                createDto(structureElements.get(6).getId(), 1),
                createDto(structureElements.get(15).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("DEXP Atlas H417",
                "Офисный ПК с оптимизированной производительностью и безопасностью", types.get(1), manufacturers.get(25)), List.of(
                createDto(structureElements.get(0).getId(), 1),
                createDto(structureElements.get(4).getId(), 1),
                createDto(structureElements.get(8).getId(), 1),
                createDto(structureElements.get(10).getId(), 1),
                createDto(structureElements.get(11).getId(), 1),
                createDto(structureElements.get(13).getId(), 1),
                createDto(structureElements.get(16).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("HP EliteDesk 800",
                "Настольный ПК бизнес-класса с улучшенной защитой и возможностью модернизации", types.get(1), manufacturers.get(0)), List.of(
                createDto(structureElements.get(1).getId(), 1),
                createDto(structureElements.get(5).getId(), 1),
                createDto(structureElements.get(8).getId(), 2),
                createDto(structureElements.get(9).getId(), 1),
                createDto(structureElements.get(10).getId(), 1),
                createDto(structureElements.get(11).getId(), 1),
                createDto(structureElements.get(14).getId(), 1),
                createDto(structureElements.get(16).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("MSI MPG Trident 3",
                "Компактный игровой настольный ПК с мощной видеокартой и процессором", types.get(1), manufacturers.get(10)), List.of(
                createDto(structureElements.get(3).getId(), 1),
                createDto(structureElements.get(5).getId(), 1),
                createDto(structureElements.get(7).getId(), 2),
                createDto(structureElements.get(9).getId(), 2),
                createDto(structureElements.get(12).getId(), 1),
                createDto(structureElements.get(14).getId(), 1),
                createDto(structureElements.get(17).getId(), 1),
                createDto(structureElements.get(18).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("Dell UltraSharp 27",
                "Профессиональный монитор с высоким разрешением и точной цветопередачей", types.get(3), manufacturers.get(1)), List.of(
                createDto(structureElements.get(19).getId(), 1),
                createDto(structureElements.get(21).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("Samsung Odyssey G7",
                "Игровой монитор с высокой частотой обновления и изогнутым экраном", types.get(3), manufacturers.get(4)), List.of(
                createDto(structureElements.get(20).getId(), 1),
                createDto(structureElements.get(22).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("Xerox WorkCentre 6515",
                "Многофункциональное устройство для печати, сканирования и копирования с поддержкой сетей", types.get(4), manufacturers.get(11)), List.of(
                createDto(structureElements.get(23).getId(), 1),
                createDto(structureElements.get(24).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("Brother HL-L2370DW",
                "Лазерный принтер для малого и среднего бизнеса с поддержкой беспроводной печати", types.get(4), manufacturers.get(12)), List.of(
                createDto(structureElements.get(24).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("TP-Link Archer C7",
                "Беспроводной маршрутизатор с поддержкой высокоскоростных стандартов Wi-Fi", types.get(5), manufacturers.get(13)), List.of(
                createDto(structureElements.get(25).getId(), 2),
                createDto(structureElements.get(26).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("HPE ProLiant DL380",
                "Сервер для корпоративного использования с масштабируемой производительностью и надежностью", types.get(0), manufacturers.get(0)), List.of(
                createDto(structureElements.get(3).getId(), 2),
                createDto(structureElements.get(7).getId(), 4),
                createDto(structureElements.get(12).getId(), 2)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("BenQ TH585",
                "Проектор с ярким изображением и высокой четкостью", types.get(6), manufacturers.get(23)), List.of(
                createDto(structureElements.get(27).getId(), 1),
                createDto(structureElements.get(28).getId(), 1)
        )));

        deviceModels.add(deviceModelService.create(new DeviceModelEntity("Cisco IP Phone 8845",
                "IP-телефон с поддержкой видеозвонков и интеграцией в корпоративные системы связи", types.get(7), manufacturers.get(9)), List.of(
                createDto(structureElements.get(29).getId(), 1),
                createDto(structureElements.get(30).getId(), 1),
                createDto(structureElements.get(31).getId(), 1)
        )));

        return deviceModels;
    }

    @Loggable
    private List<BuildingEntity> createBuildings() {
        List<BuildingEntity> buildings = new ArrayList<>();
        buildings.add(buildingService.create(new BuildingEntity("Главный корпус", "г.Москва, ул.Ленина, д.1")));
        buildings.add(buildingService.create(new BuildingEntity("Корпус B", "г. Москва, ул. Кирова, д.21")));
        buildings.add(buildingService.create(new BuildingEntity("Корпус C", "г. Москва, ул. Гагарина, д.15")));
        return buildings;
    }

    @Loggable
    private List<PositionEntity> createPositions() {
        List<PositionEntity> positions = new ArrayList<>();
        positions.add(positionService.create(new PositionEntity("Менеджер по продажам", "Поиск клиентов, заключение сделок")));
        positions.add(positionService.create(new PositionEntity("Специалист по работе с клиентами", null)));
        positions.add(positionService.create(new PositionEntity("Коммерческий директор", "Руководство отделом продаж, разработка стратегий")));
        positions.add(positionService.create(new PositionEntity("Бухгалтер", "Ведение бухгалтерского учета и отчетности")));
        positions.add(positionService.create(new PositionEntity("Финансовый аналитик", "Анализ финансовых потоков и составление прогнозов")));
        positions.add(positionService.create(new PositionEntity("Маркетолог", "Проведение маркетинговых исследований и акций")));
        positions.add(positionService.create(new PositionEntity("Контент-менеджер", "Создание и управление контентом для продвижения")));
        positions.add(positionService.create(new PositionEntity("pr-менеджер", "Взаимодействие со СМИ и формирование имиджа компании")));
        positions.add(positionService.create(new PositionEntity("hr-менеджер", "Подбор, адаптация и удержание персонала")));
        positions.add(positionService.create(new PositionEntity("Специалист по кадровому делопроизводству", "Оформление документов сотрудников")));
        positions.add(positionService.create(new PositionEntity("Разработчик", "Разработка программных продуктов")));
        positions.add(positionService.create(new PositionEntity("Системный архитектор", "Проектирование архитектуры программного обеспечения")));
        positions.add(positionService.create(new PositionEntity("Тестировщик", "Проверка качества программных продуктов")));
        positions.add(positionService.create(new PositionEntity("Инженер по информационной безопасности", "Защита IT-инфраструктуры")));
        positions.add(positionService.create(new PositionEntity("Охранник", "Физическая охрана объекта")));
        positions.add(positionService.create(new PositionEntity("Начальник службы безопасности", "Организация и контроль мероприятий по безопасности")));
        positions.add(positionService.create(new PositionEntity("Кладовщик", "Учет и хранение содержимого складов")));
        positions.add(positionService.create(new PositionEntity("Специалист по логистике", "Планирование и контроль доставок")));
        positions.add(positionService.create(new PositionEntity("Секретарь", "Ведение документооборота и прием посетителей")));
        positions.add(positionService.create(new PositionEntity("Сотрудник техподдержки", "Консультирование пользователей и устранение неисправностей")));
        positions.add(positionService.create(new PositionEntity("Системный администратор", null)));
        return positions;
    }

    @Loggable
    private List<DepartmentEntity> createDepartments(List<PositionEntity> positions) {
        List<DepartmentEntity> departments = new ArrayList<>();

        DepartmentEntity salesDept = departmentService.create(new DepartmentEntity("Отдел продаж"));
        salesDept.addPosition(positions.get(0));
        salesDept.addPosition(positions.get(1));
        salesDept.addPosition(positions.get(2));
        departments.add(salesDept);

        DepartmentEntity financeDept = departmentService.create(new DepartmentEntity("Финансовый отдел"));
        financeDept.addPosition(positions.get(3));
        financeDept.addPosition(positions.get(4));
        departments.add(financeDept);

        DepartmentEntity marketingDept = departmentService.create(new DepartmentEntity("Отдел маркетинга"));
        marketingDept.addPosition(positions.get(1));
        marketingDept.addPosition(positions.get(5));
        marketingDept.addPosition(positions.get(6));
        marketingDept.addPosition(positions.get(7));
        departments.add(marketingDept);

        DepartmentEntity hrDept = departmentService.create(new DepartmentEntity("Отдел кадров"));
        hrDept.addPosition(positions.get(8));
        hrDept.addPosition(positions.get(9));
        departments.add(hrDept);

        DepartmentEntity devDept = departmentService.create(new DepartmentEntity("Отдел разработки"));
        devDept.addPosition(positions.get(10));
        devDept.addPosition(positions.get(11));
        devDept.addPosition(positions.get(12));
        departments.add(devDept);

        DepartmentEntity securityDept = departmentService.create(new DepartmentEntity("Служба безопасности"));
        securityDept.addPosition(positions.get(13));
        securityDept.addPosition(positions.get(14));
        securityDept.addPosition(positions.get(15));
        departments.add(securityDept);

        DepartmentEntity logisticsDept = departmentService.create(new DepartmentEntity("Логистика"));
        logisticsDept.addPosition(positions.get(16));
        logisticsDept.addPosition(positions.get(17));
        departments.add(logisticsDept);

        DepartmentEntity adminDept = departmentService.create(new DepartmentEntity("Административный отдел"));
        adminDept.addPosition(positions.get(18));
        departments.add(adminDept);

        DepartmentEntity techSupportDept = departmentService.create(new DepartmentEntity("Техническая поддержка"));
        techSupportDept.addPosition(positions.get(19));
        techSupportDept.addPosition(positions.get(20));
        departments.add(techSupportDept);

        return departments;
    }

    @Loggable
    private List<EmployeeEntity> createEmployees(List<PositionEntity> positions, List<DepartmentEntity> departments) {
        List<EmployeeEntity> employees = new ArrayList<>();
        employees.add(employeeService.create(new EmployeeEntity("Кузарин", "Максим", "Романович", departments.get(1), positions.get(3))));
        employees.add(employeeService.create(new EmployeeEntity("Фарест", "Никита", "Григорьевич", departments.get(8), positions.get(19))));
        employees.add(employeeService.create(new EmployeeEntity("Баранова", "Оксана", "Геннадьевна", departments.get(3), positions.get(8))));
        employees.add(employeeService.create(new EmployeeEntity("Ерзов", "Илья", "Яковлевич", departments.get(0), positions.get(0))));
        employees.add(employeeService.create(new EmployeeEntity("Ячиков", "Афанасий", "Антонович", departments.get(3), positions.get(9))));
        employees.add(employeeService.create(new EmployeeEntity("Коновалов", "Семен", "Леонтьевич", departments.get(4), positions.get(10))));
        employees.add(employeeService.create(new EmployeeEntity("Яковиченко", "Валерий", "Алексеевич", departments.get(6), positions.get(16))));
        employees.add(employeeService.create(new EmployeeEntity("Локтионов", "Павел", "Александрович", departments.get(5), positions.get(14))));
        employees.add(employeeService.create(new EmployeeEntity("Беляков", "Константин", "Данилович", departments.get(7), positions.get(18))));
        employees.add(employeeService.create(new EmployeeEntity("Целобанова", "Зинаида", "Арсеньевна", departments.get(8), positions.get(20))));
        employees.add(employeeService.create(new EmployeeEntity("Румянцева", "Алина", "Игоревна", departments.get(2), positions.get(6))));
        employees.add(employeeService.create(new EmployeeEntity("Миронов", "Дмитрий", "Валерьевич", departments.get(0), positions.get(2))));
        employees.add(employeeService.create(new EmployeeEntity("Игнатов", "Арсений", "Федорович", departments.get(4), positions.get(11))));
        employees.add(employeeService.create(new EmployeeEntity("Солодов", "Глеб", "Олегович", departments.get(5), positions.get(13))));
        employees.add(employeeService.create(new EmployeeEntity("Захарова", "Юлия", "Романовна", departments.get(6), positions.get(17))));
        employees.add(employeeService.create(new EmployeeEntity("Щеглова", "Мария", "Сергеевна", departments.get(2), positions.get(5))));
        employees.add(employeeService.create(new EmployeeEntity("Шестаков", "Александр", "Евгеньевич", departments.get(4), positions.get(12))));
        return employees;
    }

    @Loggable
    private List<LocationEntity> createLocations(List<BuildingEntity> buildings, List<DepartmentEntity> departments, List<EmployeeEntity> employees) {
        List<LocationEntity> locations = new ArrayList<>();

        LocationEntity storage1 = locationService.create(new LocationEntity("Склад 1", LocationType.STORAGE, buildings.get(0), departments.get(6)));
        storage1.addEmployee(employees.get(6));
        locations.add(storage1);

        LocationEntity serverRoom1 = locationService.create(new LocationEntity("Серверная 1", LocationType.SERVER_ROOM, buildings.get(0), departments.get(8)));
        serverRoom1.addEmployee(employees.get(1));
        locations.add(serverRoom1);

        LocationEntity office102 = locationService.create(new LocationEntity("Кабинет 102", LocationType.OFFICE, buildings.get(1), departments.get(1)));
        office102.addEmployee(employees.get(0));
        locations.add(office102);

        LocationEntity storage2 = locationService.create(new LocationEntity("Склад 2", LocationType.STORAGE, buildings.get(1), departments.get(6)));
        storage2.addEmployee(employees.get(6));
        storage2.addEmployee(employees.get(14));
        locations.add(storage2);

        LocationEntity serverRoom2 = locationService.create(new LocationEntity("Серверная 2", LocationType.SERVER_ROOM, buildings.get(1), departments.get(8)));
        serverRoom2.addEmployee(employees.get(1));
        serverRoom2.addEmployee(employees.get(9));
        locations.add(serverRoom2);

        LocationEntity hall1 = locationService.create(new LocationEntity("Зал 1", LocationType.OFFICE, buildings.get(2), departments.get(2)));
        hall1.addEmployee(employees.get(10));
        hall1.addEmployee(employees.get(15));
        locations.add(hall1);

        LocationEntity office203 = locationService.create(new LocationEntity("Кабинет 203", LocationType.OFFICE, buildings.get(2), departments.get(3)));
        office203.addEmployee(employees.get(2));
        office203.addEmployee(employees.get(4));
        locations.add(office203);

        LocationEntity storage3 = locationService.create(new LocationEntity("Склад 3", LocationType.STORAGE, buildings.get(2), departments.get(6)));
        storage3.addEmployee(employees.get(6));
        storage3.addEmployee(employees.get(14));
        locations.add(storage3);

        LocationEntity hall2 = locationService.create(new LocationEntity("Зал 2", LocationType.OFFICE, buildings.get(0), departments.get(7)));
        hall2.addEmployee(employees.get(8));
        locations.add(hall2);

        LocationEntity office105 = locationService.create(new LocationEntity("Кабинет 105", LocationType.OFFICE, buildings.get(1), departments.get(4)));
        office105.addEmployee(employees.get(5));
        office105.addEmployee(employees.get(12));
        office105.addEmployee(employees.get(16));
        locations.add(office105);

        LocationEntity cloakroom = locationService.create(new LocationEntity("Раздевалка персонала", LocationType.CLOAKROOM, buildings.get(2), departments.get(5)));
        cloakroom.addEmployee(employees.get(7));
        cloakroom.addEmployee(employees.get(13));
        locations.add(cloakroom);

        LocationEntity office118 = locationService.create(new LocationEntity("Кабинет 118", LocationType.OFFICE, buildings.get(1), departments.get(0)));
        office118.addEmployee(employees.get(3));
        office118.addEmployee(employees.get(11));
        locations.add(office118);

        LocationEntity bathroomA = locationService.create(new LocationEntity("Санузел A", LocationType.BATHROOM, buildings.get(0), null));
        locations.add(bathroomA);

        LocationEntity bathroomB = locationService.create(new LocationEntity("Санузел B", LocationType.BATHROOM, buildings.get(2), null));
        locations.add(bathroomB);

        LocationEntity gym = locationService.create(new LocationEntity("Тренажёрный зал", LocationType.GYM, buildings.get(0), null));
        gym.addEmployee(employees.get(7));
        locations.add(gym);

        LocationEntity cafeteria = locationService.create(new LocationEntity("Столовая", LocationType.CAFETERIA, buildings.get(1), null));
        locations.add(cafeteria);

        LocationEntity recreationRoom = locationService.create(new LocationEntity("Комната для отдыха", LocationType.CAFETERIA, buildings.get(0), null));
        recreationRoom.addEmployee(employees.get(1));
        locations.add(recreationRoom);

        return locations;
    }

    @Loggable
    private void createDevices(List<DeviceModelEntity> models, List<LocationEntity> locations, List<EmployeeEntity> employees) {
        deviceService.create(new DeviceEntity("SN1593827406", Formatter.parse("2023-09-06T11:54:01Z"), Formatter.parse("2027-09-06T11:54:01Z"), 260000.00, true, "Сервер для базы данных клиентов, критически важный компонент инфраструктуры. Требует регулярного резервного копирования.", models.get(12), locations.get(1), employees.get(1)));
        deviceService.create(new DeviceEntity("SN0987654321", Formatter.parse("2023-09-06T11:54:01Z"), Formatter.parse("2025-09-06T11:54:01Z"), 72000.00, false, "Проектор неисправен, не отображает картинку. Требуется диагностика.", models.get(13), locations.get(7), employees.get(6)));
        deviceService.create(new DeviceEntity("SN2468013579", Formatter.parse("2023-09-06T11:54:01Z"), Formatter.parse("2026-09-06T11:54:01Z"), 25000.00, true, null, models.get(14), locations.get(2), employees.get(0)));
        deviceService.create(new DeviceEntity("SN9876543210", Formatter.parse("2024-03-01T09:00:00Z"), Formatter.parse("2028-03-01T09:00:00Z"), 65000.00, true, null, models.get(0), locations.get(2), employees.get(0)));
        deviceService.create(new DeviceEntity("SN1327565890", Formatter.parse("2024-04-10T11:00:00Z"), Formatter.parse("2028-04-10T11:00:00Z"), 120000.00, true, "Премиум ультрабук для отдела разработки, используется для обработки больших данных.", models.get(1), locations.get(9), employees.get(5)));
        deviceService.create(new DeviceEntity("SN1234567894", Formatter.parse("2024-01-15T10:30:00Z"), Formatter.parse("2028-01-15T10:30:00Z"), 85000.00, true, null, models.get(2), locations.get(9), employees.get(16)));
        deviceService.create(new DeviceEntity("SN1234567899", Formatter.parse("2023-11-20T14:00:00Z"), Formatter.parse("2027-11-20T14:00:00Z"), 250000.00, true, "Рабочая станция для 3D-моделирования, оснащена высокопроизводительной видеокартой.", models.get(3), locations.get(9), employees.get(12)));
        deviceService.create(new DeviceEntity("SN5647382910", Formatter.parse("2024-02-05T09:30:00Z"), Formatter.parse("2028-02-05T09:30:00Z"), 42000.00, true, "Офисный ПК для отдела кадров, подключен к системе учета рабочего времени.", models.get(4), locations.get(6), employees.get(2)));
        deviceService.create(new DeviceEntity("SN5647382911", Formatter.parse("2024-02-05T09:30:00Z"), Formatter.parse("2028-02-05T09:30:00Z"), 42000.00, true, "Офисный ПК для отдела кадров, подключен к системе учета рабочего времени.", models.get(4), locations.get(6), employees.get(2)));
        deviceService.create(new DeviceEntity("SN5647382912", Formatter.parse("2024-02-05T09:30:00Z"), Formatter.parse("2028-02-05T09:30:00Z"), 42000.00, true, "Офисный ПК для отдела кадров, подключен к системе учета рабочего времени.", models.get(4), locations.get(6), employees.get(4)));
        deviceService.create(new DeviceEntity("SN8765432109", Formatter.parse("2024-05-20T15:00:00Z"), Formatter.parse("2028-05-20T15:00:00Z"), 75000.00, true, "Настольный ПК для административного персонала, предустановлен офисный пакет.", models.get(5), locations.get(8), employees.get(8)));
        deviceService.create(new DeviceEntity("SN1357924680", Formatter.parse("2024-03-10T13:00:00Z"), Formatter.parse("2028-03-10T13:00:00Z"), 95000.00, true, "Игровой ПК для зоны отдыха сотрудников, установлен набор популярных игр.", models.get(6), locations.get(16), employees.get(1)));
        deviceService.create(new DeviceEntity("SN3145297860", Formatter.parse("2024-04-25T09:00:00Z"), Formatter.parse("2028-04-25T09:00:00Z"), 35000.00, true, "Монитор для конференц-зала.", models.get(7), locations.get(5), employees.get(10)));
        deviceService.create(new DeviceEntity("SN2468135790", Formatter.parse("2023-08-01T10:00:00Z"), Formatter.parse("2027-08-01T10:00:00Z"), 52000.00, true, "Игровой монитор в отделе разработки используется для проверки графических приложений.", models.get(8), locations.get(9), employees.get(16)));
        deviceService.create(new DeviceEntity("SN9876123450", Formatter.parse("2024-05-01T11:00:00Z"), Formatter.parse("2028-05-01T11:00:00Z"), 62000.00, true, "МФУ с возможностью удаленной печати.", models.get(9), locations.get(0), employees.get(6)));
        deviceService.create(new DeviceEntity("SN9876423697", Formatter.parse("2024-01-20T14:30:00Z"), Formatter.parse("2028-01-20T14:30:00Z"), 22000.00, true, "Персональный принтер в кабинете руководителя, используется редко.", models.get(10), locations.get(11), employees.get(11)));
        deviceService.create(new DeviceEntity("SN1263822316", Formatter.parse("2024-02-15T16:00:00Z"), Formatter.parse("2028-02-15T16:00:00Z"), 11000.00, true, "Дополнительный маршрутизатор для зоны с повышенной нагрузкой на Wi-Fi.", models.get(11), locations.get(16), employees.get(1)));
        deviceService.create(new DeviceEntity("SN4302879516", Formatter.parse("2024-03-20T10:00:00Z"), Formatter.parse("2028-03-20T10:00:00Z"), 280000.00, true, "Резервный сервер для критически важных приложений, находится в серверной 2.", models.get(12), locations.get(4), employees.get(9)));
        deviceService.create(new DeviceEntity("SN7645013829", Formatter.parse("2024-04-05T12:00:00Z"), Formatter.parse("2028-04-05T12:00:00Z"), 78000.00, true, "Проектор для зала презентаций, используется для крупных мероприятий.", models.get(13), locations.get(5), employees.get(10)));
        deviceService.create(new DeviceEntity("SN4728908212", Formatter.parse("2024-05-10T09:00:00Z"), Formatter.parse("2028-05-10T09:00:00Z"), 28000.00, true, null, models.get(14), locations.get(9), employees.get(12)));
        deviceService.create(new DeviceEntity("SN2038316991", Formatter.parse("2023-01-01T00:00:00Z"), Formatter.parse("2027-01-01T00:00:00Z"), 50000.00, true, null, models.get(0), locations.get(11), employees.get(11)));
        deviceService.create(new DeviceEntity("SN6376543730", Formatter.parse("2022-07-01T00:00:00Z"), Formatter.parse("2025-07-01T00:00:00Z"), 45000.00, true, "Офисный ПК в отделе логистики, гарантия истекает в ближайшее время.", models.get(4), locations.get(3), employees.get(14)));
        deviceService.create(new DeviceEntity("SN9775313543", Formatter.parse("2022-03-01T00:00:00Z"), Formatter.parse("2024-03-01T00:00:00Z"), 30000.00, false, "Монитор со сломанной матрицей, предназначен для списания.", models.get(7), locations.get(7), employees.get(6)));
        deviceService.create(new DeviceEntity("SN4325312106", Formatter.parse("2021-09-01T00:00:00Z"), Formatter.parse("2023-09-01T00:00:00Z"), 18000.00, true, "Принтер для черновых распечаток, активно используется. Гарантия истекла.", models.get(10), locations.get(6), employees.get(4)));
    }

    @Loggable
    private void createUser(EmployeeEntity employee) {
        userService.create(new UserEntity(appConfigurationProperties.getAdmin().getEmail(), appConfigurationProperties.getAdmin().getPassword(),
                appConfigurationProperties.getAdmin().getNumber(), UserRole.SUPER_ADMIN, employee));
    }

    private DeviceModelStructureElementIdDto createDto(Long structureElementId, int count) {
        DeviceModelStructureElementIdDto dto = new DeviceModelStructureElementIdDto();
        dto.setStructureElementId(structureElementId);
        dto.setCount(count);
        return dto;
    }
}
