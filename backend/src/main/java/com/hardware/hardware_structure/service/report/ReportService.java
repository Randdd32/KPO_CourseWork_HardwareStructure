package com.hardware.hardware_structure.service.report;

import com.hardware.hardware_structure.core.utility.Formatter;
import com.hardware.hardware_structure.model.entity.DeviceEntity;
import com.hardware.hardware_structure.model.entity.DeviceModelEntity;
import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.model.entity.LocationEntity;
import com.hardware.hardware_structure.model.entity.StructureElementModelEntity;
import com.hardware.hardware_structure.model.report.DeviceReportInfo;
import com.hardware.hardware_structure.model.report.DeviceShortInfo;
import com.hardware.hardware_structure.model.report.DeviceWithStructure;
import com.hardware.hardware_structure.model.report.EmployeeWithDevices;
import com.hardware.hardware_structure.model.report.LocationEmployeesWithDevices;
import com.hardware.hardware_structure.model.report.StructureElementInfo;
import com.hardware.hardware_structure.service.entity.DeviceService;
import com.hardware.hardware_structure.service.entity.LocationService;
import com.hardware.hardware_structure.web.dto.report.ReportDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final DeviceService deviceService;
    private final LocationService locationService;

    public JasperPrint generateDevicesByDateReport(ReportDto reportDto, boolean ignorePagination) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(getReportTemplateStream("DevicesByDate.jrxml"));
        Date dateFrom = Formatter.parse(reportDto.getDateFrom());
        Date dateTo = Formatter.parse(reportDto.getDateTo());

        List<DeviceReportInfo> devicesInfo = getDevicesReportInfo(deviceService.getBetweenPurchaseDates(dateFrom, dateTo));

        JRBeanCollectionDataSource deviceCollection = new JRBeanCollectionDataSource(devicesInfo);
        Map<String, Object> params = new HashMap<>();
        params.put("deviceCollection", deviceCollection);
        params.put("startDate", Formatter.formatToCustomString(dateFrom));
        params.put("endDate", Formatter.formatToCustomString(dateTo));
        if (ignorePagination) {
            params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        return JasperFillManager.fillReport(jasperReport, params, deviceCollection);
    }

    public JasperPrint generateDevicesWithStructureReport(ReportDto reportDto, boolean ignorePagination) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(getReportTemplateStream("DevicesWithStructure.jrxml"));

        List<DeviceWithStructure> deviceWithStructure = getDevicesWithStructure(deviceService.getByIds(reportDto.getDeviceIds()));

        JRBeanCollectionDataSource deviceCollection = new JRBeanCollectionDataSource(deviceWithStructure);
        Map<String, Object> params = new HashMap<>();
        if (ignorePagination) {
            params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        return JasperFillManager.fillReport(jasperReport, params, deviceCollection);
    }

    @Transactional
    public JasperPrint generateLocationsWithEmployeesReport(ReportDto reportDto, boolean ignorePagination) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(getReportTemplateStream("LocationsWithEmployees.jrxml"));

        List<LocationEmployeesWithDevices> locationsWithEmployees = getLocationsWithEmployees(locationService.getByIds(reportDto.getLocationIds()));

        JRBeanCollectionDataSource locationCollection = new JRBeanCollectionDataSource(locationsWithEmployees);
        Map<String, Object> params = new HashMap<>();
        if (ignorePagination) {
            params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        return JasperFillManager.fillReport(jasperReport, params, locationCollection);
    }

    private List<DeviceReportInfo> getDevicesReportInfo(List<DeviceEntity> devices) {
        return devices.stream()
                .map(deviceEntity -> {
                    DeviceReportInfo deviceInfo = new DeviceReportInfo();
                    deviceInfo.setSerialNumber(deviceEntity.getSerialNumber());
                    deviceInfo.setModelName(deviceEntity.getModel().getName());
                    deviceInfo.setPurchaseDate(Formatter.formatToCustomString(deviceEntity.getPurchaseDate()));
                    deviceInfo.setPrice(deviceEntity.getPrice());
                    deviceInfo.setWarrantyExpiryDate(Formatter.formatToCustomString(deviceEntity.getWarrantyExpiryDate()));
                    deviceInfo.setState(deviceEntity.isWorking() ? "Работает" : "Сломано");
                    deviceInfo.setLocationInfo(deviceEntity.getLocation() != null
                            ? String.format("%s (%s)", deviceEntity.getLocation().getName(), deviceEntity.getLocation().getBuilding().getName())
                            : "Нет информации");
                    return deviceInfo;
                })
                .toList();
    }

    private List<DeviceWithStructure> getDevicesWithStructure(List<DeviceEntity> devices) {
        return devices.stream()
                .map(deviceEntity -> {
                    DeviceWithStructure deviceWithStructure = new DeviceWithStructure();
                    deviceWithStructure.setSerialNumber(deviceEntity.getSerialNumber());
                    DeviceModelEntity model = deviceEntity.getModel();
                    deviceWithStructure.setModelName(model.getName());
                    deviceWithStructure.setTypeName(model.getType().getName());

                    deviceWithStructure.setWorkEfficiency(model.getWorkEfficiency());
                    deviceWithStructure.setReliability(model.getReliability());
                    deviceWithStructure.setEnergyEfficiency(model.getEnergyEfficiency());
                    deviceWithStructure.setUserFriendliness(model.getUserFriendliness());
                    deviceWithStructure.setDurability(model.getDurability());
                    deviceWithStructure.setAestheticQualities(model.getAestheticQualities());

                    deviceWithStructure.setStructureElementsInfo(model.getDeviceModelStructure().stream().map(
                            link -> {
                                StructureElementInfo structureElementInfo = new StructureElementInfo();
                                StructureElementModelEntity structureElement = link.getStructureElement();
                                structureElementInfo.setModelName(structureElement.getName());
                                structureElementInfo.setManufacturerName(structureElement.getManufacturer().getName());
                                structureElementInfo.setDescription(structureElement.getDescription());
                                structureElementInfo.setTypeName(structureElement.getType().getName());
                                structureElementInfo.setCount(link.getCount());
                                return structureElementInfo;
                            }
                    ).toList());

                    return deviceWithStructure;
                })
                .toList();
    }

    private List<LocationEmployeesWithDevices> getLocationsWithEmployees(List<LocationEntity> locations) {
        List<LocationEmployeesWithDevices> locationsWithEmployees = new ArrayList<>();
        for (LocationEntity locationEntity : locations) {
            LocationEmployeesWithDevices location = new LocationEmployeesWithDevices();
            location.setExtendedLocationName(String.format("%s - %s. (%s)",
                    locationEntity.getBuilding().getName(),
                    decapitalize(locationEntity.getName()),
                    getLocationDepartmentName(locationEntity)));

            location.setEmployees(getLocationEmployees(locationEntity));

            locationsWithEmployees.add(location);
        }
        return locationsWithEmployees;
    }

    private String getLocationDepartmentName(LocationEntity location) {
        return location.getDepartment() != null ? decapitalize(location.getDepartment().getName()) : "информация об отделе отсутствует";
    }

    private List<EmployeeWithDevices> getLocationEmployees(LocationEntity location) {
        List<EmployeeWithDevices> locationEmployees = new ArrayList<>();
        for (EmployeeEntity employeeEntity : location.getEmployees()) {
            EmployeeWithDevices employee = new EmployeeWithDevices();
            employee.setEmployeeInfo(employeeEntity.getFullName() + " - " + getEmployeePositionName(employeeEntity) + ".");

            Set<DeviceEntity> locationDevices = location.getDevices();
            employee.setDevices(employeeEntity.getDevices().stream()
                    .filter(locationDevices::contains)
                    .map(deviceEntity -> {
                                DeviceShortInfo deviceShortInfo = new DeviceShortInfo();
                                deviceShortInfo.setSerialNumber(deviceEntity.getSerialNumber());
                                deviceShortInfo.setTypeName(deviceEntity.getModel().getType().getName());
                                deviceShortInfo.setModelName(deviceEntity.getModel().getName());
                                deviceShortInfo.setWarrantyExpiryDate(Formatter.formatToCustomString(deviceEntity.getWarrantyExpiryDate()));
                                deviceShortInfo.setState(deviceEntity.isWorking() ? "Работает" : "Сломано");
                                return deviceShortInfo;
                            }
                    )
                    .toList());

            locationEmployees.add(employee);
        }
        return locationEmployees;
    }

    private String getEmployeePositionName(EmployeeEntity employee) {
        return employee.getPosition() != null ? decapitalize(employee.getPosition().getName()) : "информация о должности отсутствует";
    }

    private String decapitalize(String str) {
        if (str == null || str.isBlank()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private InputStream getReportTemplateStream(String fileName) {
        return getClass().getResourceAsStream("/static/report/" + fileName);
    }
}
