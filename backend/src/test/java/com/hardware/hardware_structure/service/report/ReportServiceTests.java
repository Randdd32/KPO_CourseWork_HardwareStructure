package com.hardware.hardware_structure.service.report;

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
import com.hardware.hardware_structure.model.enums.LocationType;
import com.hardware.hardware_structure.service.AbstractIntegrationTest;
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
import com.hardware.hardware_structure.web.dto.entity.DeviceModelStructureElementIdDto;
import com.hardware.hardware_structure.web.dto.report.ReportDto;
import net.sf.jasperreports.engine.JasperPrint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

class ReportServiceTests extends AbstractIntegrationTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceModelService deviceModelService;
    @Autowired
    private DeviceTypeService deviceTypeService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private StructureElementModelService structureElementModelService;
    @Autowired
    private StructureElementTypeService structureElementTypeService;

    private DeviceEntity device;
    private LocationEntity location;

    @BeforeEach
    void prepareData() {
        DeviceTypeEntity type = deviceTypeService.create(new DeviceTypeEntity("Laptop"));
        ManufacturerEntity manufacturer = manufacturerService.create(new ManufacturerEntity("Dell"));
        BuildingEntity building = buildingService.create(new BuildingEntity("Main Corp", "Street 1"));
        DepartmentEntity department = departmentService.create(new DepartmentEntity("IT Dept"));
        PositionEntity position = positionService.create(new PositionEntity("Developer", "Code writer"));

        StructureElementTypeEntity cpuType = structureElementTypeService.create(new StructureElementTypeEntity("CPU"));
        StructureElementModelEntity cpuElement = structureElementModelService.create(new StructureElementModelEntity(
                "Intel i7", "High-end", cpuType, manufacturer, 90, 90, 90, 90, 90, 90));

        List<DeviceModelStructureElementIdDto> structure = List.of(
                createStructureDto(cpuElement.getId(), 1)
        );
        DeviceModelEntity model = deviceModelService.create(new DeviceModelEntity("XPS 15", "Top laptop", type, manufacturer), structure);

        location = locationService.create(new LocationEntity("Office 101", LocationType.OFFICE, building, department));
        EmployeeEntity employee = employeeService.create(new EmployeeEntity("Doe", "John", "Mal", department, position));

        Date purchaseDate = createDate(2023, 1, 15);
        Date expiryDate = createDate(2026, 1, 15);
        device = deviceService.create(new DeviceEntity(
                "SN-REPORT-001", purchaseDate, expiryDate, 2000.0, true, "Note", model, location, employee
        ));
    }

    @Test
    void generateDevicesByDateReportTest() throws Exception {
        ReportDto reportDto = new ReportDto();
        reportDto.setDateFrom("2023-01-01T11:54:01Z");
        reportDto.setDateTo("2023-12-31T11:54:01Z");

        JasperPrint jasperPrint = reportService.generateDevicesByDateReport(reportDto, true);

        Assertions.assertNotNull(jasperPrint);
        Assertions.assertEquals("DevicesByDate", jasperPrint.getName());

        Assertions.assertFalse(jasperPrint.getPages().isEmpty(), "Отчет должен содержать хотя бы одну страницу");
    }

    @Test
    void generateDevicesByDateReport_NoDataTest() throws Exception {
        ReportDto reportDto = new ReportDto();
        reportDto.setDateFrom("2020-01-01T11:54:01Z");
        reportDto.setDateTo("2020-12-31T11:54:01Z");

        JasperPrint jasperPrint = reportService.generateDevicesByDateReport(reportDto, true);

        Assertions.assertNotNull(jasperPrint);
    }

    @Test
    void generateDevicesWithStructureReportTest() throws Exception {
        ReportDto reportDto = new ReportDto();
        reportDto.setDeviceIds(List.of(device.getId()));

        JasperPrint jasperPrint = reportService.generateDevicesWithStructureReport(reportDto, true);

        Assertions.assertNotNull(jasperPrint);
        Assertions.assertFalse(jasperPrint.getPages().isEmpty());
    }

    @Test
    void generateLocationsWithEmployeesReportTest() throws Exception {
        ReportDto reportDto = new ReportDto();
        reportDto.setLocationIds(List.of(location.getId()));

        JasperPrint jasperPrint = reportService.generateLocationsWithEmployeesReport(reportDto, true);

        Assertions.assertNotNull(jasperPrint);
        Assertions.assertFalse(jasperPrint.getPages().isEmpty());
    }

    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private DeviceModelStructureElementIdDto createStructureDto(Long id, int count) {
        DeviceModelStructureElementIdDto dto = new DeviceModelStructureElementIdDto();
        dto.setStructureElementId(id);
        dto.setCount(count);
        return dto;
    }
}
