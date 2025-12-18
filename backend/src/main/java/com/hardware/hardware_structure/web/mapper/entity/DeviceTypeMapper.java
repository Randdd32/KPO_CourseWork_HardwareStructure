package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.DeviceTypeEntity;
import com.hardware.hardware_structure.web.dto.entity.DeviceTypeDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceTypeMapper {
    private final ModelMapper modelMapper;

    public DeviceTypeDto toDto(DeviceTypeEntity entity) {
        return modelMapper.map(entity, DeviceTypeDto.class);
    }

    public DeviceTypeEntity toEntity(DeviceTypeDto dto) {
        return modelMapper.map(dto, DeviceTypeEntity.class);
    }
}
