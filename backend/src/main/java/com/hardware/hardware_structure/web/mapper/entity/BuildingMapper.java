package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.BuildingEntity;
import com.hardware.hardware_structure.web.dto.entity.BuildingDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildingMapper {
    private final ModelMapper modelMapper;

    public BuildingDto toDto(BuildingEntity entity) {
        return modelMapper.map(entity, BuildingDto.class);
    }

    public BuildingEntity toEntity(BuildingDto dto) {
        return modelMapper.map(dto, BuildingEntity.class);
    }
}
