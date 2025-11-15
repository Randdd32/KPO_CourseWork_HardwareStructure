package com.hardware.hardware_structure.web.controller;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.ManufacturerService;
import com.hardware.hardware_structure.web.dto.entity.ManufacturerDto;
import com.hardware.hardware_structure.web.mapper.entity.ManufacturerMapper;
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
public class ManufacturerController {
    private static final String MANUFACTURER_URL = "/manufacturer";

    private final ManufacturerService manufacturerService;
    private final ManufacturerMapper manufacturerMapper;

    @GetMapping(MANUFACTURER_URL)
    public List<ManufacturerDto> getAll(@RequestParam(name = "name", defaultValue = "") String name) {
        return manufacturerService.getAll(name).stream()
                .map(manufacturerMapper::toDto)
                .toList();
    }

    @GetMapping(MANUFACTURER_URL + "/ids")
    public List<ManufacturerDto> getByIds(@RequestParam(name = "ids", required = false) List<Long> ids) {
        return manufacturerService.getByIds(ids).stream()
                .map(manufacturerMapper::toDto)
                .toList();
    }

    @GetMapping(MANUFACTURER_URL + "/{id}")
    public ManufacturerDto get(@PathVariable(name = "id") Long id) {
        return manufacturerMapper.toDto(manufacturerService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + MANUFACTURER_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public ManufacturerDto create(@RequestBody @Valid ManufacturerDto dto) {
        return manufacturerMapper.toDto(manufacturerService.create(manufacturerMapper.toEntity(dto)));
    }

    @PutMapping(Constants.ADMIN_PREFIX + MANUFACTURER_URL + "/{id}")
    public ManufacturerDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid ManufacturerDto dto) {
        return manufacturerMapper.toDto(manufacturerService.update(id, manufacturerMapper.toEntity(dto)));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + MANUFACTURER_URL + "/{id}")
    public ManufacturerDto delete(@PathVariable(name = "id") Long id) {
        return manufacturerMapper.toDto(manufacturerService.delete(id));
    }
}
