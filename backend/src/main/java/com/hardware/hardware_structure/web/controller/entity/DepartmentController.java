package com.hardware.hardware_structure.web.controller.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.DepartmentService;
import com.hardware.hardware_structure.web.dto.entity.DepartmentDto;
import com.hardware.hardware_structure.web.mapper.entity.DepartmentMapper;
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
public class DepartmentController {
    private static final String DEPARTMENT_URL = "/department";

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @GetMapping(DEPARTMENT_URL)
    public List<DepartmentDto> getAll(@RequestParam(name = "name", defaultValue = "") String name) {
        return departmentService.getAll(name).stream()
                .map(departmentMapper::toDto)
                .toList();
    }

    @GetMapping(DEPARTMENT_URL + "/ids")
    public List<DepartmentDto> getByIds(@RequestParam(name = "ids", required = false) List<Long> ids) {
        return departmentService.getByIds(ids).stream()
                .map(departmentMapper::toDto)
                .toList();
    }

    @GetMapping(DEPARTMENT_URL + "/{id}")
    public DepartmentDto get(@PathVariable(name = "id") Long id) {
        return departmentMapper.toDto(departmentService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + DEPARTMENT_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto create(@RequestBody @Valid DepartmentDto dto) {
        return departmentMapper.toDto(departmentService.create(departmentMapper.toEntity(dto)));
    }

    @PutMapping(Constants.ADMIN_PREFIX + DEPARTMENT_URL + "/{id}")
    public DepartmentDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid DepartmentDto dto) {
        return departmentMapper.toDto(departmentService.update(id, departmentMapper.toEntity(dto)));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + DEPARTMENT_URL + "/{id}")
    public DepartmentDto delete(@PathVariable(name = "id") Long id) {
        return departmentMapper.toDto(departmentService.delete(id));
    }
}
