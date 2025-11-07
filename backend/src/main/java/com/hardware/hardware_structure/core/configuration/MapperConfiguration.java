package com.hardware.hardware_structure.core.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class MapperConfiguration {
    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
