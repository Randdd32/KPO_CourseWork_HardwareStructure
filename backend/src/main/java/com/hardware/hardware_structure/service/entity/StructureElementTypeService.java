package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.StructureElementTypeEntity;
import com.hardware.hardware_structure.repository.StructureElementTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StructureElementTypeService extends AbstractEntityService<StructureElementTypeEntity> {
    private final StructureElementTypeRepository repository;

    @Transactional(readOnly = true)
    public List<StructureElementTypeEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public StructureElementTypeEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(StructureElementTypeEntity.class, id));
    }

    @Transactional
    public StructureElementTypeEntity create(StructureElementTypeEntity entity) {
        validate(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public StructureElementTypeEntity update(long id, StructureElementTypeEntity entity) {
        validate(entity, id);
        final StructureElementTypeEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        return repository.save(existsEntity);
    }

    @Transactional
    public StructureElementTypeEntity delete(long id) {
        final StructureElementTypeEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(StructureElementTypeEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Structure element type entity is null");
        }
        validateStringField(entity.getName(), "Structure element type name");

        final Optional<StructureElementTypeEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Structure element type with name %s already exists", entity.getName())
            );
        }
    }
}
