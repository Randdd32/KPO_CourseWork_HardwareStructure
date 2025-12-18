package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.ManufacturerEntity;
import com.hardware.hardware_structure.web.dto.entity.ManufacturerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManufacturerMapper {
    private final ModelMapper modelMapper;

    public ManufacturerDto toDto(ManufacturerEntity entity) {
        return modelMapper.map(entity, ManufacturerDto.class);
    }

    public ManufacturerEntity toEntity(ManufacturerDto dto) {
        return modelMapper.map(dto, ManufacturerEntity.class);
    }
}
