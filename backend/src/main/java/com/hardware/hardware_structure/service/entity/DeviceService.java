package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.model.entity.DeviceEntity;
import com.hardware.hardware_structure.model.enums.DeviceSortType;
import com.hardware.hardware_structure.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DeviceService extends AbstractEntityService<DeviceEntity> {
    private final DeviceRepository repository;

    @Transactional(readOnly = true)
    public List<DeviceEntity> getAll() {
        return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public Page<DeviceEntity> getAll(int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return repository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public List<DeviceEntity> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public List<DeviceEntity> getBetweenPurchaseDates(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date must not be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date must not be null");
        }
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        return repository.findByPurchaseDateBetweenOrderByPurchaseDateDesc(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public Page<DeviceEntity> getAllByFilters(
            List<Long> manufacturerIds,
            List<Long> buildingIds,
            List<Long> locationIds,
            List<Long> employeeIds,
            List<Long> typeIds,
            List<Long> departmentIds,
            Boolean isWorking,
            Double priceFrom,
            Double priceTo,
            Date purchaseDateFrom,
            Date purchaseDateTo,
            Date warrantyDateFrom,
            Date warrantyDateTo,
            String searchInfo,
            DeviceSortType sortType,
            int page,
            int size
    ) {
        Sort sort = Sort.by(getSortInfo(sortType));
        Pageable pageRequest = PageRequest.of(page, size, sort);

        return repository.findDevicesByFilters(
                nullIfEmpty(manufacturerIds),
                nullIfEmpty(buildingIds),
                nullIfEmpty(locationIds),
                nullIfEmpty(employeeIds),
                nullIfEmpty(typeIds),
                nullIfEmpty(departmentIds),
                isWorking,
                priceFrom,
                priceTo,
                purchaseDateFrom,
                purchaseDateTo,
                warrantyDateFrom,
                warrantyDateTo,
                (searchInfo != null && !searchInfo.isBlank()) ? searchInfo.toLowerCase() : null,
                pageRequest
        );
    }

    @Transactional(readOnly = true)
    public DeviceEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(DeviceEntity.class, id));
    }

    @Transactional
    public DeviceEntity create(DeviceEntity entity) {
        validate(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public DeviceEntity update(long id, DeviceEntity entity) {
        validate(entity, id);
        final DeviceEntity existsEntity = get(id);
        existsEntity.setSerialNumber(entity.getSerialNumber());
        existsEntity.setPurchaseDate(entity.getPurchaseDate());
        existsEntity.setWarrantyExpiryDate(entity.getWarrantyExpiryDate());
        existsEntity.setPrice(entity.getPrice());
        existsEntity.setWorking(entity.isWorking());
        existsEntity.setFurtherInformation(entity.getFurtherInformation());
        existsEntity.setModel(entity.getModel());
        existsEntity.setLocation(entity.getLocation());
        existsEntity.setEmployee(entity.getEmployee());
        return repository.save(existsEntity);
    }

    @Transactional
    public DeviceEntity delete(long id) {
        final DeviceEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(DeviceEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("Device entity is null");
        }
        validateStringField(entity.getSerialNumber(), "Serial number");
        if (entity.getPurchaseDate() == null) {
            throw new IllegalArgumentException("Purchase date must not be null");
        }
        if (entity.getWarrantyExpiryDate() == null) {
            throw new IllegalArgumentException("Warranty expiry date must not be null");
        }
        if (entity.getWarrantyExpiryDate().before(entity.getPurchaseDate())) {
            throw new IllegalArgumentException("Warranty expiry date cannot be before purchase date");
        }
        if (entity.getPrice() < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
        if (entity.getModel() == null) {
            throw new IllegalArgumentException("Device model must not be null");
        }

        final Optional<DeviceEntity> existingEntity = repository.findBySerialNumberIgnoreCase(entity.getSerialNumber());
        if (existingEntity.isPresent() && !existingEntity.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("Device with serial number %s already exists", entity.getSerialNumber())
            );
        }
    }

    private Sort.Order getSortInfo(DeviceSortType sortType) {
        if (sortType == null) {
            return new Sort.Order(Sort.Direction.DESC, "purchaseDate");
        }
        return switch (sortType) {
            case PRICE_ASC -> new Sort.Order(Sort.Direction.ASC, "price");
            case PRICE_DESC -> new Sort.Order(Sort.Direction.DESC, "price");
            case PURCHASE_DATE_ASC -> new Sort.Order(Sort.Direction.ASC, "purchaseDate");
            case PURCHASE_DATE_DESC -> new Sort.Order(Sort.Direction.DESC, "purchaseDate");
            case IS_WORKING_ASC -> new Sort.Order(Sort.Direction.ASC, "isWorking");
            case IS_WORKING_DESC -> new Sort.Order(Sort.Direction.DESC, "isWorking");
            case ID_ASC -> new Sort.Order(Sort.Direction.ASC, "id");
            case ID_DESC -> new Sort.Order(Sort.Direction.DESC, "id");
        };
    }

    private <T> List<T> nullIfEmpty(List<T> list) {
        return (list != null && !list.isEmpty()) ? list : null;
    }
}
