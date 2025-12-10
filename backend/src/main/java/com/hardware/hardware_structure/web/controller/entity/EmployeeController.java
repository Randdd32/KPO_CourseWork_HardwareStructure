package com.hardware.hardware_structure.web.controller.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.EmployeeService;
import com.hardware.hardware_structure.web.dto.entity.EmployeeDto;
import com.hardware.hardware_structure.web.dto.pagination.PageDto;
import com.hardware.hardware_structure.web.mapper.entity.EmployeeMapper;
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

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL)
@RequiredArgsConstructor
public class EmployeeController {
    private static final String EMPLOYEE_URL = "/employee";

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @GetMapping(EMPLOYEE_URL)
    public PageDto<EmployeeDto> getAll(
            @RequestParam(name = "fullName", defaultValue = "") String fullName,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return PageDtoMapper.toDto(employeeService.getAll(fullName, page, size), employeeMapper::toDto);
    }

    @GetMapping(EMPLOYEE_URL + "/ids")
    public List<EmployeeDto> getByIds(@RequestParam(name = "ids", required = false) List<Long> ids) {
        return employeeService.getByIds(ids).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @GetMapping(EMPLOYEE_URL + "/{id}")
    public EmployeeDto get(@PathVariable(name = "id") Long id) {
        return employeeMapper.toDto(employeeService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + EMPLOYEE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto create(@RequestBody @Valid EmployeeDto dto) {
        return employeeMapper.toDto(employeeService.create(employeeMapper.toEntity(dto)));
    }

    @PutMapping(Constants.ADMIN_PREFIX + EMPLOYEE_URL + "/{id}")
    public EmployeeDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid EmployeeDto dto) {
        return employeeMapper.toDto(employeeService.update(id, employeeMapper.toEntity(dto)));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + EMPLOYEE_URL + "/{id}")
    public EmployeeDto delete(@PathVariable(name = "id") Long id) {
        return employeeMapper.toDto(employeeService.delete(id));
    }
}
