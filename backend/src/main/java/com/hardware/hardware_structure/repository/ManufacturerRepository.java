package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.ManufacturerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends CrudRepository<ManufacturerEntity, Long>, PagingAndSortingRepository<ManufacturerEntity, Long> {
    Optional<ManufacturerEntity> findByNameIgnoreCase(String name);

    @Query("""
    SELECT m FROM ManufacturerEntity m
    WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', m.name, '%'))
    ORDER BY m.id ASC
    """)
    List<ManufacturerEntity> findByNameContainingIgnoreCase(@Param("name") String name);
}
