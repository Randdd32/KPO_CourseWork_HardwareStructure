package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import com.hardware.hardware_structure.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class EmployeeService extends AbstractEntityService<EmployeeEntity>  {
    private final EmployeeRepository repository;

    @Transactional(readOnly = true)
    public List<EmployeeEntity> getAll(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByFullNameContainingIgnoreCase(fullName);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeEntity> getAll(String fullName, int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
        if (fullName == null || fullName.isBlank()) {
            return repository.findAll(pageRequest);
        }
        return repository.findByFullNameContainingIgnoreCase(fullName, pageRequest);
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public EmployeeEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(EmployeeEntity.class, id));
    }

    @Transactional
    public EmployeeEntity create(EmployeeEntity entity) {
        validate(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public EmployeeEntity update(long id, EmployeeEntity entity) {
        validate(entity, null);
        final EmployeeEntity existsEntity = get(id);
        existsEntity.setFirstName(entity.getFirstName());
        existsEntity.setLastName(entity.getLastName());
        existsEntity.setPatronymic(entity.getPatronymic());
        existsEntity.setDepartment(entity.getDepartment());
        existsEntity.setPosition(entity.getPosition());
        return repository.save(existsEntity);
    }

    @Transactional
    public EmployeeEntity delete(long id) {
        final EmployeeEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(EmployeeEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Employee entity is null");
        }
        validateStringField(entity.getLastName(), "Employee last name");
        if (!entity.getLastName().matches(Constants.NAME_PATTERN)) {
            throw new IllegalArgumentException("Employee last name has invalid format: " + entity.getLastName());
        }
        validateStringField(entity.getFirstName(), "Employee first name");
        if (!entity.getFirstName().matches(Constants.NAME_PATTERN)) {
            throw new IllegalArgumentException("Employee first name has invalid format: " + entity.getFirstName());
        }
        if (entity.getPatronymic() != null && !entity.getPatronymic().matches(Constants.NAME_PATTERN)) {
            throw new IllegalArgumentException("Employee patronymic has invalid format: " + entity.getPatronymic());
        }
    }
}
