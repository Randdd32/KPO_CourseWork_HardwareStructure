package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.model.entity.LocationEntity;
import com.hardware.hardware_structure.service.entity.BuildingService;
import com.hardware.hardware_structure.service.entity.DepartmentService;
import com.hardware.hardware_structure.service.entity.EmployeeService;
import com.hardware.hardware_structure.web.dto.entity.LocationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LocationMapper {
    private final ModelMapper modelMapper;
    private final BuildingService buildingService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public LocationDto toDto(LocationEntity entity) {
        LocationDto dto = modelMapper.map(entity, LocationDto.class);
        dto.setNameWithBuilding(entity.getName() + " (" + entity.getBuilding().getName() + ")");
        dto.setBuildingId(entity.getBuilding().getId());
        dto.setBuildingName(entity.getBuilding().getName());
        dto.setDepartmentId(entity.getDepartment() != null ? entity.getDepartment().getId() : null);
        dto.setDepartmentName(entity.getDepartment() != null ? entity.getDepartment().getName() : null);
        dto.setEmployees(
                entity.getEmployees()
                        .stream()
                        .map(employeeMapper::toDto)
                        .toList()
        );
        return dto;
    }

    public LocationEntity toEntity(LocationDto dto) {
        LocationEntity entity = modelMapper.map(dto, LocationEntity.class);
        entity.setBuilding(buildingService.get(dto.getBuildingId()));
        entity.setDepartment(dto.getDepartmentId() != null ? departmentService.get(dto.getDepartmentId()) : null);
        List<EmployeeEntity> employees = employeeService.getByIds(dto.getEmployeeIds());
        entity.setEmployees(new HashSet<>(employees));
        return entity;
    }
}
