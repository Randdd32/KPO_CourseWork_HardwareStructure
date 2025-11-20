package com.hardware.hardware_structure.service.entity;

import org.springframework.stereotype.Service;

@Service
public abstract class AbstractEntityService<T> {
    protected void validateStringField(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty");
        }
    }

    protected abstract void validate(T entity, Long id);
}
