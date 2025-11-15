package com.hardware.hardware_structure.web.controller;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.BuildingService;
import com.hardware.hardware_structure.web.dto.entity.BuildingDto;
import com.hardware.hardware_structure.web.mapper.entity.BuildingMapper;
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
public class BuildingController {
    private static final String BUILDING_URL = "/building";

    private final BuildingService buildingService;
    private final BuildingMapper buildingMapper;

    @GetMapping(BUILDING_URL)
    public List<BuildingDto> getAll(@RequestParam(name = "name", defaultValue = "") String name) {
        return buildingService.getAll(name).stream()
                .map(buildingMapper::toDto)
                .toList();
    }

    @GetMapping(BUILDING_URL + "/ids")
    public List<BuildingDto> getByIds(@RequestParam(name = "ids", required = false) List<Long> ids) {
        return buildingService.getByIds(ids).stream()
                .map(buildingMapper::toDto)
                .toList();
    }

    @GetMapping(BUILDING_URL + "/{id}")
    public BuildingDto get(@PathVariable(name = "id") Long id) {
        return buildingMapper.toDto(buildingService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + BUILDING_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public BuildingDto create(@RequestBody @Valid BuildingDto dto) {
        return buildingMapper.toDto(buildingService.create(buildingMapper.toEntity(dto)));
    }

    @PutMapping(Constants.ADMIN_PREFIX + BUILDING_URL + "/{id}")
    public BuildingDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid BuildingDto dto) {
        return buildingMapper.toDto(buildingService.update(id, buildingMapper.toEntity(dto)));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + BUILDING_URL + "/{id}")
    public BuildingDto delete(@PathVariable(name = "id") Long id) {
        return buildingMapper.toDto(buildingService.delete(id));
    }
}
