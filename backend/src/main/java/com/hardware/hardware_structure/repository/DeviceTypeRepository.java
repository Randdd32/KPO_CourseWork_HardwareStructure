package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.DeviceTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeviceTypeRepository extends CrudRepository<DeviceTypeEntity, Long>,
        PagingAndSortingRepository<DeviceTypeEntity, Long> {
    Optional<DeviceTypeEntity> findByNameIgnoreCase(String name);

    @Query("""
    SELECT t FROM DeviceTypeEntity t
    WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', t.name, '%'))
    ORDER BY t.id ASC
    """)
    List<DeviceTypeEntity> findByNameContainingIgnoreCase(@Param("name") String name);
}
