package com.hardware.hardware_structure.web.controller.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.PositionService;
import com.hardware.hardware_structure.web.dto.entity.PositionDto;
import com.hardware.hardware_structure.web.dto.pagination.PageDto;
import com.hardware.hardware_structure.web.mapper.entity.PositionMapper;
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
@RequestMapping(Constants.API_URL + Constants.ADMIN_PREFIX + "/position")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;
    private final PositionMapper positionMapper;

    @GetMapping
    public PageDto<PositionDto> getAll(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return PageDtoMapper.toDto(positionService.getAll(name, page, size), positionMapper::toDto);
    }

    @GetMapping("/{id}")
    public PositionDto get(@PathVariable(name = "id") Long id) {
        return positionMapper.toDto(positionService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PositionDto create(@RequestBody @Valid PositionDto dto) {
        return positionMapper.toDto(positionService.create(positionMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public PositionDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid PositionDto dto) {
        return positionMapper.toDto(positionService.update(id, positionMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public PositionDto delete(@PathVariable(name = "id") Long id) {
        return positionMapper.toDto(positionService.delete(id));
    }
}
