package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.core.utility.Formatter;
import com.hardware.hardware_structure.model.entity.DeviceEntity;
import com.hardware.hardware_structure.service.entity.DeviceModelService;
import com.hardware.hardware_structure.service.entity.EmployeeService;
import com.hardware.hardware_structure.service.entity.LocationService;
import com.hardware.hardware_structure.web.dto.entity.DeviceDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceMapper {
    private final ModelMapper modelMapper;
    private final DeviceModelService modelService;
    private final LocationService locationService;
    private final EmployeeService employeeService;

    public DeviceDto toDto(DeviceEntity entity) {
        DeviceDto dto = modelMapper.map(entity, DeviceDto.class);
        dto.setModelId(entity.getModel().getId());
        dto.setModelName(entity.getModel().getName());
        dto.setIsWorking(entity.isWorking());
        dto.setLocationId(entity.getLocation() != null ? entity.getLocation().getId() : null);
        dto.setLocationName(entity.getLocation() != null ? entity.getLocation().getName() : null);
        dto.setEmployeeId(entity.getEmployee() != null ? entity.getEmployee().getId() : null);
        dto.setEmployeeFullName(entity.getEmployee() != null ? entity.getEmployee().getFullName() : null);
        dto.setEmployeeInfo(
                entity.getEmployee() != null
                        ? entity.getEmployee().getFullName() +
                        (entity.getEmployee().getPosition() != null ? ". " + entity.getEmployee().getPosition().getName() : "")
                        : null
        );
        dto.setPurchaseDate(Formatter.format(entity.getPurchaseDate()));
        dto.setWarrantyExpiryDate(Formatter.format(entity.getWarrantyExpiryDate()));
        dto.setDepartmentName(
                entity.getEmployee() != null
                        ? (entity.getEmployee().getDepartment() != null ? entity.getEmployee().getDepartment().getName() : null)
                        : null
        );
        dto.setBuildingName(
                entity.getLocation() != null
                        ? entity.getLocation().getBuilding().getName()
                        : null
        );
        dto.setTypeName(entity.getModel().getType().getName());
        dto.setManufacturerName(entity.getModel().getManufacturer().getName());
        return dto;
    }

    public DeviceEntity toEntity(DeviceDto dto) {
        DeviceEntity entity = modelMapper.map(dto, DeviceEntity.class);
        entity.setWorking(dto.getIsWorking());
        entity.setModel(modelService.get(dto.getModelId()));
        entity.setLocation(dto.getLocationId() != null ? locationService.get(dto.getLocationId()) : null);
        entity.setEmployee(dto.getEmployeeId() != null ? employeeService.get(dto.getEmployeeId()) : null);
        entity.setPurchaseDate(Formatter.parse(dto.getPurchaseDate()));
        entity.setWarrantyExpiryDate(Formatter.parse(dto.getWarrantyExpiryDate()));
        return entity;
    }
}
