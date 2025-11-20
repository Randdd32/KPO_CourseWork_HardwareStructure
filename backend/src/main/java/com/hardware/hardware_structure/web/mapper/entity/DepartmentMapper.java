package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.service.entity.PositionService;
import com.hardware.hardware_structure.web.dto.entity.DepartmentDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {
    private final ModelMapper modelMapper;
    private final PositionMapper positionMapper;
    private final PositionService positionService;

    public DepartmentDto toDto(DepartmentEntity entity) {
        DepartmentDto dto = modelMapper.map(entity, DepartmentDto.class);
        dto.setPositions(
                entity.getPositions()
                        .stream()
                        .map(positionMapper::toDto)
                        .toList()
        );
        return dto;
    }

    public DepartmentEntity toEntity(DepartmentDto dto) {
        DepartmentEntity entity = modelMapper.map(dto, DepartmentEntity.class);
        List<PositionEntity> positions = positionService.getByIds(dto.getPositionIds());
        entity.setPositions(new HashSet<>(positions));
        return entity;
    }
}
