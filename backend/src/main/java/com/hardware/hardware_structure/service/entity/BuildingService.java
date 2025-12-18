package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.BuildingEntity;
import com.hardware.hardware_structure.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BuildingService extends AbstractEntityService<BuildingEntity> {
    private final BuildingRepository repository;

    @Transactional(readOnly = true)
    public List<BuildingEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<BuildingEntity> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public BuildingEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(BuildingEntity.class, id));
    }

    @Transactional
    public BuildingEntity create(BuildingEntity entity) {
        validate(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public BuildingEntity update(long id, BuildingEntity entity) {
        validate(entity, id);
        final BuildingEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        existsEntity.setAddress(entity.getAddress());
        return repository.save(existsEntity);
    }

    @Transactional
    public BuildingEntity delete(long id) {
        final BuildingEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(BuildingEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Building entity is null");
        }
        validateStringField(entity.getName(), "Building name");
        validateStringField(entity.getAddress(), "Building address");
        final Optional<BuildingEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Building with name %s already exists", entity.getName())
            );
        }
    }
}
