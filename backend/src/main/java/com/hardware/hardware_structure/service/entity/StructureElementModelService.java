package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.StructureElementModelEntity;
import com.hardware.hardware_structure.repository.StructureElementModelRepository;
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
public class StructureElementModelService extends AbstractEntityService<StructureElementModelEntity> {
    private final StructureElementModelRepository repository;

    @Transactional(readOnly = true)
    public List<StructureElementModelEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public Page<StructureElementModelEntity> getAll(String name, int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
        if (name == null || name.isBlank()) {
            return repository.findAll(pageRequest);
        }
        return repository.findByNameContainingIgnoreCase(name, pageRequest);
    }

    @Transactional(readOnly = true)
    public StructureElementModelEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(StructureElementModelEntity.class, id));
    }

    @Transactional
    public StructureElementModelEntity create(StructureElementModelEntity entity) {
        validate(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public StructureElementModelEntity update(long id, StructureElementModelEntity entity) {
        validate(entity, id);
        final StructureElementModelEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        existsEntity.setDescription(entity.getDescription());
        existsEntity.setType(entity.getType());
        existsEntity.setManufacturer(entity.getManufacturer());
        existsEntity.setWorkEfficiency(entity.getWorkEfficiency());
        existsEntity.setReliability(entity.getReliability());
        existsEntity.setEnergyEfficiency(entity.getEnergyEfficiency());
        existsEntity.setUserFriendliness(entity.getUserFriendliness());
        existsEntity.setDurability(entity.getDurability());
        existsEntity.setAestheticQualities(entity.getAestheticQualities());
        return repository.save(existsEntity);
    }

    @Transactional
    public StructureElementModelEntity delete(long id) {
        final StructureElementModelEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(StructureElementModelEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Structure element model entity is null");
        }
        validateStringField(entity.getName(), "Structure element model name");
        if (entity.getType() == null) {
            throw new IllegalArgumentException("Structure element model type must not be null");
        }
        if (entity.getManufacturer() == null) {
            throw new IllegalArgumentException("Structure element model manufacturer must not be null");
        }
        validateCharacteristic(entity.getWorkEfficiency(), "Work efficiency");
        validateCharacteristic(entity.getReliability(), "Reliability");
        validateCharacteristic(entity.getEnergyEfficiency(), "Energy efficiency");
        validateCharacteristic(entity.getUserFriendliness(), "User friendliness");
        validateCharacteristic(entity.getDurability(), "Durability");
        validateCharacteristic(entity.getAestheticQualities(), "Aesthetic qualities");

        final Optional<StructureElementModelEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Structure element model with name %s already exists", entity.getName())
            );
        }
    }

    private void validateCharacteristic(int value, String fieldName) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException(String.format("%s must be between 0 and 100", fieldName));
        }
    }
}
