package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.DeviceModelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeviceModelRepository extends CrudRepository<DeviceModelEntity, Long>, PagingAndSortingRepository<DeviceModelEntity, Long> {
    Optional<DeviceModelEntity> findByNameIgnoreCase(String name);

    @Query("""
    SELECT m FROM DeviceModelEntity m
    WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', m.name, '%'))
    ORDER BY m.id ASC
    """)
    List<DeviceModelEntity> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("""
    SELECT m FROM DeviceModelEntity m
    WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', m.name, '%'))
    ORDER BY m.id ASC
    """)
    Page<DeviceModelEntity> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
