package com.hardware.hardware_structure.web.controller;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.core.utility.Formatter;
import com.hardware.hardware_structure.model.enums.DeviceSortType;
import com.hardware.hardware_structure.service.entity.DeviceService;
import com.hardware.hardware_structure.web.dto.entity.DeviceDto;
import com.hardware.hardware_structure.web.dto.pagination.PageDto;
import com.hardware.hardware_structure.web.mapper.entity.DeviceMapper;
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

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(Constants.API_URL)
@RequiredArgsConstructor
public class DeviceController {
    private static final String DEVICE_URL = "/device";

    private final DeviceService deviceService;
    private final DeviceMapper deviceMapper;

    @GetMapping(DEVICE_URL)
    public PageDto<DeviceDto> getAllByFilters(
            @RequestParam(name = "manufacturerIds", required = false) List<Long> manufacturerIds,
            @RequestParam(name = "buildingIds", required = false) List<Long> buildingIds,
            @RequestParam(name = "locationIds", required = false) List<Long> locationIds,
            @RequestParam(name = "employeeIds", required = false) List<Long> employeeIds,
            @RequestParam(name = "typeIds", required = false) List<Long> typeIds,
            @RequestParam(name = "departmentIds", required = false) List<Long> departmentIds,
            @RequestParam(name = "isWorking", required = false) Boolean isWorking,
            @RequestParam(name = "priceFrom", required = false) Double priceFrom,
            @RequestParam(name = "priceTo", required = false) Double priceTo,
            @RequestParam(name = "purchaseDateFrom", required = false) String purchaseDateFromStr,
            @RequestParam(name = "purchaseDateTo", required = false) String purchaseDateToStr,
            @RequestParam(name = "warrantyDateFrom", required = false) String warrantyDateFromStr,
            @RequestParam(name = "warrantyDateTo", required = false) String warrantyDateToStr,
            @RequestParam(name = "searchInfo", required = false) String searchInfo,
            @RequestParam(name = "sortType", defaultValue = "PURCHASE_DATE_DESC") DeviceSortType sortType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        Date purchaseDateFrom = purchaseDateFromStr != null ? Formatter.parse(purchaseDateFromStr) : null;
        Date purchaseDateTo = purchaseDateToStr != null ? Formatter.parse(purchaseDateToStr) : null;
        Date warrantyDateFrom = warrantyDateFromStr != null ? Formatter.parse(warrantyDateFromStr) : null;
        Date warrantyDateTo = warrantyDateToStr != null ? Formatter.parse(warrantyDateToStr) : null;

        return PageDtoMapper.toDto(
                deviceService.getAllByFilters(
                        manufacturerIds,
                        buildingIds,
                        locationIds,
                        employeeIds,
                        typeIds,
                        departmentIds,
                        isWorking,
                        priceFrom,
                        priceTo,
                        purchaseDateFrom,
                        purchaseDateTo,
                        warrantyDateFrom,
                        warrantyDateTo,
                        searchInfo,
                        sortType,
                        page,
                        size
                ),
                deviceMapper::toDto
        );
    }

    @GetMapping(DEVICE_URL + "/{id}")
    public DeviceDto get(@PathVariable long id) {
        return deviceMapper.toDto(deviceService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + DEVICE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceDto create(@RequestBody @Valid DeviceDto dto) {
        return deviceMapper.toDto(deviceService.create(deviceMapper.toEntity(dto)));
    }

    @PutMapping(Constants.ADMIN_PREFIX + DEVICE_URL + "/{id}")
    public DeviceDto update(@PathVariable long id, @RequestBody @Valid DeviceDto dto) {
        return deviceMapper.toDto(deviceService.update(id, deviceMapper.toEntity(dto)));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + DEVICE_URL + "/{id}")
    public DeviceDto delete(@PathVariable long id) {
        return deviceMapper.toDto(deviceService.delete(id));
    }
}
