package com.hardware.hardware_structure.web.controller.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.service.entity.UserService;
import com.hardware.hardware_structure.web.dto.entity.UserDto;
import com.hardware.hardware_structure.web.dto.pagination.PageDto;
import com.hardware.hardware_structure.web.mapper.entity.UserMapper;
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
@RequestMapping(Constants.API_URL)
@RequiredArgsConstructor
public class UserController {
    private static final String USER_URL = "/user";

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(Constants.ADMIN_PREFIX + USER_URL)
    public PageDto<UserDto> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return PageDtoMapper.toDto(userService.getAll(page, size), userMapper::toDto);
    }

    @GetMapping(USER_URL + "/{id}")
    public UserDto get(@PathVariable(name = "id") Long id) {
        return userMapper.toDto(userService.get(id));
    }

    @PostMapping(Constants.ADMIN_PREFIX + USER_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto dto) {
        return userMapper.toDto(userService.create(userMapper.toEntity(dto)));
    }

    @PutMapping(Constants.ADMIN_PREFIX + USER_URL + "/{id}")
    public UserDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid UserDto dto) {
        return userMapper.toDto(userService.update(id, userMapper.toEntity(dto)));
    }

    @DeleteMapping(Constants.ADMIN_PREFIX + USER_URL + "/{id}")
    public UserDto delete(@PathVariable(name = "id") Long id) {
        return userMapper.toDto(userService.delete(id));
    }
}
