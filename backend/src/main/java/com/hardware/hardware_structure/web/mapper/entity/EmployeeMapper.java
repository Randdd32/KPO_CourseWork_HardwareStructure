package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.service.entity.DepartmentService;
import com.hardware.hardware_structure.service.entity.PositionService;
import com.hardware.hardware_structure.web.dto.entity.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {
    private final ModelMapper modelMapper;
    private final DepartmentService departmentService;
    private final PositionService positionService;

    public EmployeeDto toDto(EmployeeEntity entity) {
        EmployeeDto dto = modelMapper.map(entity, EmployeeDto.class);
        dto.setFullName(entity.getFullName());
        dto.setDepartmentId(entity.getDepartment() != null ? entity.getDepartment().getId() : null);
        dto.setDepartmentName(entity.getDepartment() != null ? entity.getDepartment().getName() : null);
        dto.setPositionId(entity.getPosition() != null ? entity.getPosition().getId() : null);
        dto.setPositionName(entity.getPosition() != null ? entity.getPosition().getName() : null);
        return dto;
    }

    public EmployeeEntity toEntity(EmployeeDto dto) {
        EmployeeEntity entity = modelMapper.map(dto, EmployeeEntity.class);
        entity.setDepartment(dto.getDepartmentId() != null ? departmentService.get(dto.getDepartmentId()) : null);
        entity.setPosition(dto.getPositionId() != null ? positionService.get(dto.getPositionId()) : null);
        return entity;
    }
}
