package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.model.entity.LocationEntity;
import com.hardware.hardware_structure.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class LocationService extends AbstractEntityService<LocationEntity> {
    private final LocationRepository repository;

    @Transactional(readOnly = true)
    public List<LocationEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public Page<LocationEntity> getAll(String name, int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
        if (name == null || name.isBlank()) {
            return repository.findAll(pageRequest);
        }
        return repository.findByNameContainingIgnoreCase(name, pageRequest);
    }

    @Transactional(readOnly = true)
    public List<LocationEntity> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public LocationEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(LocationEntity.class, id));
    }

    @Transactional
    public LocationEntity create(LocationEntity entity) {
        validate(entity, null);
        LocationEntity location = new LocationEntity(entity.getName(), entity.getType(), entity.getBuilding(), entity.getDepartment());
        for (EmployeeEntity employee : entity.getEmployees()) {
            location.addEmployee(employee);
        }
        return repository.save(location);
    }

    @Transactional
    public LocationEntity update(long id, LocationEntity entity) {
        validate(entity, id);
        final LocationEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        existsEntity.setType(entity.getType());
        existsEntity.setBuilding(entity.getBuilding());
        existsEntity.setDepartment(entity.getDepartment());
        syncEmployees(existsEntity, entity.getEmployees());
        return repository.save(existsEntity);
    }

    @Transactional
    public LocationEntity delete(long id) {
        final LocationEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(LocationEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Location entity is null");
        }
        validateStringField(entity.getName(), "Location name");
        if (entity.getType() == null) {
            throw new IllegalArgumentException("Location type must not be null");
        }
        if (entity.getBuilding() == null) {
            throw new IllegalArgumentException("Location building must not be null");
        }

        final Optional<LocationEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Location with name %s already exists", entity.getName())
            );
        }
    }

    private void syncEmployees(LocationEntity existsEntity, Set<EmployeeEntity> updatedEmployees) {
        Set<EmployeeEntity> employeesToRemove = existsEntity.getEmployees().stream()
                .filter(employee -> !updatedEmployees.contains(employee))
                .collect(Collectors.toSet());
        for (EmployeeEntity employee : employeesToRemove) {
            existsEntity.deleteEmployee(employee);
        }

        for (EmployeeEntity employee : updatedEmployees) {
            if (!existsEntity.getEmployees().contains(employee)) {
                existsEntity.addEmployee(employee);
            }
        }
    }
}
