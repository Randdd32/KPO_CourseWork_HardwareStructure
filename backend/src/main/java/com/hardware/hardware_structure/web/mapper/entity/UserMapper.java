package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.service.entity.EmployeeService;
import com.hardware.hardware_structure.web.dto.entity.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;

    public UserDto toDto(UserEntity entity) {
        UserDto dto = modelMapper.map(entity, UserDto.class);
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setEmployeeFullName(entity.getEmployee().getFullName());
        return dto;
    }

    public UserEntity toEntity(UserDto dto) {
        UserEntity entity = modelMapper.map(dto, UserEntity.class);
        entity.setEmployee(employeeService.get(dto.getEmployeeId()));
        return entity;
    }
}
