package com.hardware.hardware_structure.web.controller;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.StructureElementTypeService;
import com.hardware.hardware_structure.web.dto.entity.StructureElementTypeDto;
import com.hardware.hardware_structure.web.mapper.entity.StructureElementTypeMapper;
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
@RequestMapping(Constants.API_URL + Constants.ADMIN_PREFIX + "/structure-element-type")
@RequiredArgsConstructor
public class StructureElementTypeController {
    private final StructureElementTypeService structureElementTypeService;
    private final StructureElementTypeMapper structureElementTypeMapper;

    @GetMapping
    public List<StructureElementTypeDto> getAll(@RequestParam(name = "name", defaultValue = "") String name) {
        return structureElementTypeService.getAll(name).stream()
                .map(structureElementTypeMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public StructureElementTypeDto get(@PathVariable(name = "id") Long id) {
        return structureElementTypeMapper.toDto(structureElementTypeService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StructureElementTypeDto create(@RequestBody @Valid StructureElementTypeDto dto) {
        return structureElementTypeMapper.toDto(
                structureElementTypeService.create(structureElementTypeMapper.toEntity(dto))
        );
    }

    @PutMapping("/{id}")
    public StructureElementTypeDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid StructureElementTypeDto dto) {
        return structureElementTypeMapper.toDto(
                structureElementTypeService.update(id, structureElementTypeMapper.toEntity(dto))
        );
    }

    @DeleteMapping("/{id}")
    public StructureElementTypeDto delete(@PathVariable(name = "id") Long id) {
        return structureElementTypeMapper.toDto(structureElementTypeService.delete(id));
    }
}
