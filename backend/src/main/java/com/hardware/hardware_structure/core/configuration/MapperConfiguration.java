package com.hardware.hardware_structure.core.configuration;

import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.DeviceModelEntity;
import com.hardware.hardware_structure.model.entity.LocationEntity;
import com.hardware.hardware_structure.web.dto.entity.DepartmentDto;
import com.hardware.hardware_structure.web.dto.entity.DeviceModelDto;
import com.hardware.hardware_structure.web.dto.entity.LocationDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(DepartmentEntity.class, DepartmentDto.class).addMappings(mapper -> mapper.skip(DepartmentDto::setPositions));
        modelMapper.typeMap(DeviceModelEntity.class, DeviceModelDto.class).addMappings(mapper -> mapper.skip(DeviceModelDto::setStructureElements));
        modelMapper.typeMap(LocationEntity.class, LocationDto.class).addMappings(mapper -> mapper.skip(LocationDto::setEmployees));
        return modelMapper;
    }
}
