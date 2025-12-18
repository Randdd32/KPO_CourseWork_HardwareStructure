package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.StructureElementTypeEntity;
import com.hardware.hardware_structure.web.dto.entity.StructureElementTypeDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StructureElementTypeMapper {
    private final ModelMapper modelMapper;

    public StructureElementTypeDto toDto(StructureElementTypeEntity entity) {
        return modelMapper.map(entity, StructureElementTypeDto.class);
    }

    public StructureElementTypeEntity toEntity(StructureElementTypeDto dto) {
        return modelMapper.map(dto, StructureElementTypeEntity.class);
    }
}
