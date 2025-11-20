package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.StructureElementModelEntity;
import com.hardware.hardware_structure.service.entity.ManufacturerService;
import com.hardware.hardware_structure.service.entity.StructureElementTypeService;
import com.hardware.hardware_structure.web.dto.entity.StructureElementModelDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StructureElementModelMapper {
    private final ModelMapper modelMapper;
    private final StructureElementTypeService typeService;
    private final ManufacturerService manufacturerService;

    public StructureElementModelDto toDto(StructureElementModelEntity entity) {
        StructureElementModelDto dto = modelMapper.map(entity, StructureElementModelDto.class);
        dto.setTypeId(entity.getType().getId());
        dto.setTypeName(entity.getType().getName());
        dto.setManufacturerId(entity.getManufacturer().getId());
        dto.setManufacturerName(entity.getManufacturer().getName());
        return dto;
    }

    public StructureElementModelEntity toEntity(StructureElementModelDto dto) {
        StructureElementModelEntity entity = modelMapper.map(dto, StructureElementModelEntity.class);
        entity.setType(typeService.get(dto.getTypeId()));
        entity.setManufacturer(manufacturerService.get(dto.getManufacturerId()));
        return entity;
    }
}
