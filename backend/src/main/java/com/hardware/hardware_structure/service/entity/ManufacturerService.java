package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.ManufacturerEntity;
import com.hardware.hardware_structure.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ManufacturerService extends AbstractEntityService<ManufacturerEntity> {
    private final ManufacturerRepository repository;

    @Transactional(readOnly = true)
    public List<ManufacturerEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public ManufacturerEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ManufacturerEntity.class, id));
    }

    @Transactional(readOnly = true)
    public List<ManufacturerEntity> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false).toList();
    }

    @Transactional
    public ManufacturerEntity create(ManufacturerEntity entity) {
        validate(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public ManufacturerEntity update(long id, ManufacturerEntity entity) {
        validate(entity, id);
        final ManufacturerEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        return repository.save(existsEntity);
    }

    @Transactional
    public ManufacturerEntity delete(long id) {
        final ManufacturerEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(ManufacturerEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Manufacturer entity is null");
        }
        validateStringField(entity.getName(), "Manufacturer name");

        final Optional<ManufacturerEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Manufacturer with name %s already exists", entity.getName())
            );
        }
    }
}
