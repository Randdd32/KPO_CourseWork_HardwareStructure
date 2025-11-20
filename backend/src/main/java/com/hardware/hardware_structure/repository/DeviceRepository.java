package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends CrudRepository<DeviceEntity, Long>, PagingAndSortingRepository<DeviceEntity, Long> {
    Optional<DeviceEntity> findBySerialNumberIgnoreCase(String serialNumber);

    List<DeviceEntity> findByPurchaseDateBetweenOrderByPurchaseDateDesc(Date startDate, Date endDate);

    @Query("""
    SELECT d FROM DeviceEntity d
    JOIN d.model m
    JOIN m.manufacturer manuf
    JOIN m.type t
    LEFT JOIN d.location loc
    LEFT JOIN loc.building b
    LEFT JOIN d.employee emp
    LEFT JOIN emp.department dep
    WHERE
      (:manufacturerIds IS NULL OR manuf.id IN :manufacturerIds)
      AND (:buildingIds IS NULL OR b.id IN :buildingIds)
      AND (:locationIds IS NULL OR loc.id IN :locationIds)
      AND (:employeeIds IS NULL OR emp.id IN :employeeIds)
      AND (:typeIds IS NULL OR t.id IN :typeIds)
      AND (:departmentIds IS NULL OR dep.id IN :departmentIds)
      AND (:isWorking IS NULL OR d.isWorking = :isWorking)
      AND (:priceFrom IS NULL OR d.price >= :priceFrom)
      AND (:priceTo IS NULL OR d.price <= :priceTo)
      AND (COALESCE(:purchaseDateFrom, null) IS NULL OR d.purchaseDate >= :purchaseDateFrom)
      AND (COALESCE(:purchaseDateTo, null) IS NULL OR d.purchaseDate <= :purchaseDateTo)
      AND (COALESCE(:warrantyDateFrom, null) IS NULL OR d.warrantyExpiryDate >= :warrantyDateFrom)
      AND (COALESCE(:warrantyDateTo, null) IS NULL OR d.warrantyExpiryDate <= :warrantyDateTo)
      AND (
            :searchInfo IS NULL
            OR LOWER(d.serialNumber) LIKE %:searchInfo%
            OR LOWER(d.furtherInformation) LIKE %:searchInfo%
            OR LOWER(m.name) LIKE %:searchInfo%
            OR :searchInfo LIKE CONCAT('%', LOWER(m.name), '%')
            OR LOWER(t.name) LIKE %:searchInfo%
            OR :searchInfo LIKE CONCAT('%', LOWER(t.name), '%')
            OR LOWER(manuf.name) LIKE %:searchInfo%
            OR :searchInfo LIKE CONCAT('%', LOWER(manuf.name), '%')
      )
    """)
    Page<DeviceEntity> findDevicesByFilters(
            @Param("manufacturerIds") List<Long> manufacturerIds,
            @Param("buildingIds") List<Long> buildingIds,
            @Param("locationIds") List<Long> locationIds,
            @Param("employeeIds") List<Long> employeeIds,
            @Param("typeIds") List<Long> typeIds,
            @Param("departmentIds") List<Long> departmentIds,
            @Param("isWorking") Boolean isWorking,
            @Param("priceFrom") Double priceFrom,
            @Param("priceTo") Double priceTo,
            @Param("purchaseDateFrom") Date purchaseDateFrom,
            @Param("purchaseDateTo") Date purchaseDateTo,
            @Param("warrantyDateFrom") Date warrantyDateFrom,
            @Param("warrantyDateTo") Date warrantyDateTo,
            @Param("searchInfo") String searchInfo,
            Pageable pageable
    );
}
