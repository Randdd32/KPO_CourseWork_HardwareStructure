package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.web.dto.entity.PositionDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionMapper {
    private final ModelMapper modelMapper;

    public PositionDto toDto(PositionEntity entity) {
        return modelMapper.map(entity, PositionDto.class);
    }

    public PositionEntity toEntity(PositionDto dto) {
        return modelMapper.map(dto, PositionEntity.class);
    }
}
