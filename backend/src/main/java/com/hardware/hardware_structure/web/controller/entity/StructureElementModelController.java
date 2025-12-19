package com.hardware.hardware_structure.web.controller.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.StructureElementModelService;
import com.hardware.hardware_structure.web.dto.entity.StructureElementModelDto;
import com.hardware.hardware_structure.web.dto.pagination.PageDto;
import com.hardware.hardware_structure.web.mapper.entity.StructureElementModelMapper;
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
@RequestMapping(Constants.API_URL + Constants.ADMIN_PREFIX + "/structure-element-model")
@RequiredArgsConstructor
public class StructureElementModelController {
    private final StructureElementModelService modelService;
    private final StructureElementModelMapper modelMapper;

    @GetMapping
    public PageDto<StructureElementModelDto> getAll(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return PageDtoMapper.toDto(
                modelService.getAll(name, page, size),
                modelMapper::toDto
        );
    }

    @GetMapping("/{id}")
    public StructureElementModelDto get(@PathVariable(name = "id") Long id) {
        return modelMapper.toDto(modelService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StructureElementModelDto create(@RequestBody @Valid StructureElementModelDto dto) {
        return modelMapper.toDto(modelService.create(modelMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public StructureElementModelDto update(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid StructureElementModelDto dto) {
        return modelMapper.toDto(modelService.update(id, modelMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public StructureElementModelDto delete(@PathVariable(name = "id") Long id) {
        return modelMapper.toDto(modelService.delete(id));
    }
}
