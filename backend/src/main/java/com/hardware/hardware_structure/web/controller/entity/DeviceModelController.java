package com.hardware.hardware_structure.web.controller.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.DeviceModelService;
import com.hardware.hardware_structure.web.dto.entity.DeviceModelDto;
import com.hardware.hardware_structure.web.dto.pagination.PageDto;
import com.hardware.hardware_structure.web.mapper.entity.DeviceModelMapper;
import com.hardware.hardware_structure.web.mapper.pagination.PageDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URL)
@RequiredArgsConstructor
public class DeviceModelController {
    private static final String DEVICE_MODEL_URL = "/device-model";

    private final DeviceModelService deviceModelService;
    private final DeviceModelMapper modelMapper;

    @GetMapping(DEVICE_MODEL_URL)
    public PageDto<DeviceModelDto> getAll(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return PageDtoMapper.toDto(
                deviceModelService.getAll(name, page, size),
                modelMapper::toDto
        );
    }

    @GetMapping(DEVICE_MODEL_URL + "/{id}")
    public DeviceModelDto get(@PathVariable(name = "id") Long id) {
        return modelMapper.toDto(deviceModelService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + DEVICE_MODEL_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceModelDto create(@RequestBody @Valid DeviceModelDto dto) {
        return modelMapper.toDto(deviceModelService.create(modelMapper.toEntity(dto), dto.getStructureElementsIds()));
    }

    @PutMapping(Constants.ADMIN_PREFIX + DEVICE_MODEL_URL + "/{id}")
    public DeviceModelDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid DeviceModelDto dto) {
        return modelMapper.toDto(deviceModelService.update(id, modelMapper.toEntity(dto), dto.getStructureElementsIds()));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + DEVICE_MODEL_URL + "/{id}")
    public DeviceModelDto delete(@PathVariable(name = "id") Long id) {
        return modelMapper.toDto(deviceModelService.delete(id));
    }
}
