package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import com.hardware.hardware_structure.model.entity.PositionEntity;
import com.hardware.hardware_structure.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DepartmentService extends AbstractEntityService<DepartmentEntity> {
    private final DepartmentRepository repository;

    @Transactional(readOnly = true)
    public List<DepartmentEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<DepartmentEntity> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public DepartmentEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(DepartmentEntity.class, id));
    }

    @Transactional
    public DepartmentEntity create(DepartmentEntity entity) {
        validate(entity, null);
        DepartmentEntity department = new DepartmentEntity(entity.getName());
        for (PositionEntity positionEntity : entity.getPositions()) {
            department.addPosition(positionEntity);
        }
        return repository.save(department);
    }

    @Transactional
    public DepartmentEntity update(long id, DepartmentEntity entity) {
        validate(entity, id);
        final DepartmentEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        syncPositions(existsEntity, entity.getPositions());
        return repository.save(existsEntity);
    }

    @Transactional
    public DepartmentEntity delete(long id) {
        final DepartmentEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(DepartmentEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Department entity is null");
        }
        validateStringField(entity.getName(), "Department name");

        final Optional<DepartmentEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Department with name %s already exists", entity.getName())
            );
        }
    }

    private void syncPositions(DepartmentEntity existsEntity, Set<PositionEntity> updatedPositions) {
        Set<PositionEntity> positionsToRemove = existsEntity.getPositions().stream()
                .filter(position -> !updatedPositions.contains(position))
                .collect(Collectors.toSet());
        for (PositionEntity position : positionsToRemove) {
            existsEntity.deletePosition(position);
        }

        for (PositionEntity position : updatedPositions) {
            if (!existsEntity.getPositions().contains(position)) {
                existsEntity.addPosition(position);
            }
        }
    }
}
