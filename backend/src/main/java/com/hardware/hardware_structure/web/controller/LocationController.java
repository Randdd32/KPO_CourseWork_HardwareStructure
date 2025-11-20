package com.hardware.hardware_structure.web.controller;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.LocationService;
import com.hardware.hardware_structure.web.dto.entity.LocationDto;
import com.hardware.hardware_structure.web.dto.pagination.PageDto;
import com.hardware.hardware_structure.web.mapper.entity.LocationMapper;
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
public class LocationController {
    private static final String LOCATION_URL = "/location";

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @GetMapping(LOCATION_URL)
    public PageDto<LocationDto> getAll(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return PageDtoMapper.toDto(locationService.getAll(name, page, size), locationMapper::toDto);
    }

    @GetMapping(LOCATION_URL + "/ids")
    public List<LocationDto> getByIds(@RequestParam(name = "ids", required = false) List<Long> ids) {
        return locationService.getByIds(ids).stream()
                .map(locationMapper::toDto)
                .toList();
    }

    @GetMapping(LOCATION_URL + "/{id}")
    public LocationDto get(@PathVariable(name = "id") Long id) {
        return locationMapper.toDto(locationService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + LOCATION_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto create(@RequestBody @Valid LocationDto dto) {
        return locationMapper.toDto(locationService.create(locationMapper.toEntity(dto)));
    }

    @PutMapping(Constants.ADMIN_PREFIX + LOCATION_URL + "/{id}")
    public LocationDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid LocationDto dto) {
        return locationMapper.toDto(locationService.update(id, locationMapper.toEntity(dto)));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + LOCATION_URL + "/{id}")
    public LocationDto delete(@PathVariable(name = "id") Long id) {
        return locationMapper.toDto(locationService.delete(id));
    }
}
