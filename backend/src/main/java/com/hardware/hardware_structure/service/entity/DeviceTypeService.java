package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.DeviceTypeEntity;
import com.hardware.hardware_structure.repository.DeviceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DeviceTypeService extends AbstractEntityService<DeviceTypeEntity> {
    private final DeviceTypeRepository repository;

    @Transactional(readOnly = true)
    public List<DeviceTypeEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<DeviceTypeEntity> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public DeviceTypeEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(DeviceTypeEntity.class, id));
    }

    @Transactional
    public DeviceTypeEntity create(DeviceTypeEntity entity) {
        validate(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public DeviceTypeEntity update(long id, DeviceTypeEntity entity) {
        validate(entity, id);
        final DeviceTypeEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        return repository.save(existsEntity);
    }

    @Transactional
    public DeviceTypeEntity delete(long id) {
        final DeviceTypeEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(DeviceTypeEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Device type entity is null");
        }
        validateStringField(entity.getName(), "Device type name");

        final Optional<DeviceTypeEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Device type with name %s already exists", entity.getName())
            );
        }
    }
}
