package com.hardware.hardware_structure.web.mapper.entity;

import com.hardware.hardware_structure.model.entity.DeviceModelEntity;
import com.hardware.hardware_structure.service.entity.DeviceTypeService;
import com.hardware.hardware_structure.service.entity.ManufacturerService;
import com.hardware.hardware_structure.web.dto.entity.DeviceModelDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DeviceModelMapper {
    private final ModelMapper modelMapper;
    private final DeviceTypeService typeService;
    private final ManufacturerService manufacturerService;
    private final DeviceModelStructureElementMapper deviceModelStructureElementMapper;

    public DeviceModelDto toDto(DeviceModelEntity entity) {
        DeviceModelDto dto = modelMapper.map(entity, DeviceModelDto.class);
        dto.setTypeId(entity.getType().getId());
        dto.setTypeName(entity.getType().getName());
        dto.setManufacturerId(entity.getManufacturer().getId());
        dto.setManufacturerName(entity.getManufacturer().getName());

        dto.setWorkEfficiency(entity.getWorkEfficiency());
        dto.setReliability(entity.getReliability());
        dto.setEnergyEfficiency(entity.getEnergyEfficiency());
        dto.setUserFriendliness(entity.getUserFriendliness());
        dto.setDurability(entity.getDurability());
        dto.setAestheticQualities(entity.getAestheticQualities());

        dto.setStructureElements(
                entity.getDeviceModelStructure().stream()
                        .map(deviceModelStructureElementMapper::toDto)
                        .toList()
        );
        return dto;
    }

    public DeviceModelEntity toEntity(DeviceModelDto dto) {
        DeviceModelEntity entity = modelMapper.map(dto, DeviceModelEntity.class);
        entity.setType(typeService.get(dto.getTypeId()));
        entity.setManufacturer(manufacturerService.get(dto.getManufacturerId()));
        if (entity.getDeviceModelStructure() == null) {
            entity.setDeviceModelStructure(new HashSet<>());
        }
        return entity;
    }
}
