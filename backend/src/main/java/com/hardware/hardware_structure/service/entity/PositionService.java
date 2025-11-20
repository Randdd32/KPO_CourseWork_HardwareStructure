package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PositionService extends AbstractEntityService<PositionEntity> {
    private final PositionRepository repository;

    @Transactional(readOnly = true)
    public List<PositionEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public Page<PositionEntity> getAll(String name, int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
        if (name == null || name.isBlank()) {
            return repository.findAll(pageRequest);
        }
        return repository.findByNameContainingIgnoreCase(name, pageRequest);
    }

    @Transactional(readOnly = true)
    public List<PositionEntity> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public PositionEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(PositionEntity.class, id));
    }

    @Transactional
    public PositionEntity create(PositionEntity entity) {
        validate(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public PositionEntity update(long id, PositionEntity entity) {
        validate(entity, id);
        final PositionEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        existsEntity.setDescription(entity.getDescription());
        return repository.save(existsEntity);
    }

    @Transactional
    public PositionEntity delete(long id) {
        final PositionEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(PositionEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Position entity is null");
        }
        validateStringField(entity.getName(), "Position name");

        final Optional<PositionEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Position with name %s already exists", entity.getName())
            );
        }
    }
}
