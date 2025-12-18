package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.DeviceModelEntity;
import com.hardware.hardware_structure.model.entity.DeviceModelStructureElementEntity;
import com.hardware.hardware_structure.repository.DeviceModelRepository;
import com.hardware.hardware_structure.web.dto.entity.DeviceModelStructureElementIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DeviceModelService extends AbstractEntityService<DeviceModelEntity> {
    private final DeviceModelRepository repository;
    private final StructureElementModelService structureElementModelService;

    @Transactional(readOnly = true)
    public List<DeviceModelEntity> getAll(String name) {
        if (name == null || name.isBlank()) {
            return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public Page<DeviceModelEntity> getAll(String name, int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
        if (name == null || name.isBlank()) {
            return repository.findAll(pageRequest);
        }
        return repository.findByNameContainingIgnoreCase(name, pageRequest);
    }

    @Transactional(readOnly = true)
    public DeviceModelEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(DeviceModelEntity.class, id));
    }

    @Transactional
    public DeviceModelEntity create(DeviceModelEntity entity, List<DeviceModelStructureElementIdDto> elementsInfo) {
        validate(entity, null);
        DeviceModelEntity model = repository.save(entity);
        elementsInfo.forEach(idDto -> {
            final DeviceModelStructureElementEntity link = new DeviceModelStructureElementEntity();
            link.setStructureElement(structureElementModelService.get(idDto.getStructureElementId()));
            link.setCount(idDto.getCount());
            link.setDeviceModel(model);
        });
        return model;
    }

    @Transactional
    public DeviceModelEntity update(long id, DeviceModelEntity entity, List<DeviceModelStructureElementIdDto> elementsInfo) {
        validate(entity, id);
        final DeviceModelEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        existsEntity.setDescription(entity.getDescription());
        existsEntity.setType(entity.getType());
        existsEntity.setManufacturer(entity.getManufacturer());
        final DeviceModelEntity updatedEntity = repository.save(existsEntity);
        syncDeviceModelStructure(updatedEntity, elementsInfo);
        return updatedEntity;
    }

    @Transactional
    public DeviceModelEntity delete(long id) {
        final DeviceModelEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(DeviceModelEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Device model entity is null");
        }
        validateStringField(entity.getName(), "Device model name");
        if (entity.getType() == null) {
            throw new IllegalArgumentException("Device model type must not be null");
        }
        if (entity.getManufacturer() == null) {
            throw new IllegalArgumentException("Device model manufacturer must not be null");
        }

        final Optional<DeviceModelEntity> existingEntity = repository.findByNameIgnoreCase(entity.getName());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Device model with name %s already exists", entity.getName())
            );
        }
    }

    private void syncDeviceModelStructure(DeviceModelEntity existsEntity, List<DeviceModelStructureElementIdDto> elementsInfo) {
        Map<Long, DeviceModelStructureElementEntity> existingStructureMap = existsEntity.getDeviceModelStructure().stream()
                .collect(Collectors.toMap(
                        e -> e.getStructureElement().getId(),
                        e -> e
                ));
        Map<Long, DeviceModelStructureElementIdDto> updatedStructureMap = elementsInfo.stream()
                .collect(Collectors.toMap(
                        DeviceModelStructureElementIdDto::getStructureElementId,
                        e -> e
                ));

        Set<DeviceModelStructureElementEntity> elementsToRemove = existsEntity.getDeviceModelStructure().stream()
                .filter(existingElement -> !updatedStructureMap.containsKey(existingElement.getStructureElement().getId()))
                .collect(Collectors.toSet());
        elementsToRemove.forEach(existsEntity::deleteStructureElement);

        for (DeviceModelStructureElementIdDto dto : elementsInfo) {
            Long structureElementId = dto.getStructureElementId();
            Integer count = dto.getCount();

            DeviceModelStructureElementEntity existingElement = existingStructureMap.get(structureElementId);
            if (existingElement != null) {
                existingElement.setCount(count);
            } else {
                final DeviceModelStructureElementEntity newElement = new DeviceModelStructureElementEntity();
                newElement.setDeviceModel(existsEntity);
                newElement.setStructureElement(structureElementModelService.get(structureElementId));
                newElement.setCount(count);
            }
        }
    }
}
