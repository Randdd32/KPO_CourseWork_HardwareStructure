package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.DeviceModelStructureElementEntity;
import com.hardware.hardware_structure.web.dto.entity.DeviceModelStructureElementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceModelStructureElementMapper {
    private final StructureElementModelMapper structureElementModelMapper;

    public DeviceModelStructureElementDto toDto(DeviceModelStructureElementEntity entity) {
        DeviceModelStructureElementDto dto = new DeviceModelStructureElementDto();
        dto.setStructureElement(structureElementModelMapper.toDto(entity.getStructureElement()));
        dto.setCount(entity.getCount());
        return dto;
    }
}
