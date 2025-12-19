package com.hardware.hardware_structure.web.controller.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.DeviceTypeService;
import com.hardware.hardware_structure.web.dto.entity.DeviceTypeDto;
import com.hardware.hardware_structure.web.mapper.entity.DeviceTypeMapper;
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

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL)
@RequiredArgsConstructor
public class DeviceTypeController {
    private static final String DEVICE_TYPE_URL = "/device-type";

    private final DeviceTypeService deviceTypeService;
    private final DeviceTypeMapper deviceTypeMapper;

    @GetMapping(DEVICE_TYPE_URL)
    public List<DeviceTypeDto> getAll(@RequestParam(name = "name", defaultValue = "") String name) {
        return deviceTypeService.getAll(name).stream()
                .map(deviceTypeMapper::toDto)
                .toList();
    }

    @GetMapping(DEVICE_TYPE_URL + "/ids")
    public List<DeviceTypeDto> getByIds(@RequestParam(name = "ids", required = false) List<Long> ids) {
        return deviceTypeService.getByIds(ids).stream()
                .map(deviceTypeMapper::toDto)
                .toList();
    }

    @GetMapping(DEVICE_TYPE_URL + "/{id}")
    public DeviceTypeDto get(@PathVariable(name = "id") Long id) {
        return deviceTypeMapper.toDto(deviceTypeService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + DEVICE_TYPE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceTypeDto create(@RequestBody @Valid DeviceTypeDto dto) {
        return deviceTypeMapper.toDto(deviceTypeService.create(deviceTypeMapper.toEntity(dto)));
    }

    @PutMapping(Constants.ADMIN_PREFIX + DEVICE_TYPE_URL + "/{id}")
    public DeviceTypeDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid DeviceTypeDto dto) {
        return deviceTypeMapper.toDto(deviceTypeService.update(id, deviceTypeMapper.toEntity(dto)));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + DEVICE_TYPE_URL + "/{id}")
    public DeviceTypeDto delete(@PathVariable(name = "id") Long id) {
        return deviceTypeMapper.toDto(deviceTypeService.delete(id));
    }
}
