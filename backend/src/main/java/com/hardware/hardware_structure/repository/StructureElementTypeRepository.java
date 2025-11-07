package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.StructureElementTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StructureElementTypeRepository extends CrudRepository<StructureElementTypeEntity, Long>,
        PagingAndSortingRepository<StructureElementTypeEntity, Long> {
    Optional<StructureElementTypeEntity> findByNameIgnoreCase(String name);

    @Query("""
    SELECT t FROM StructureElementTypeEntity t
    WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', t.name, '%'))
    ORDER BY t.id ASC
    """)
    List<StructureElementTypeEntity> findByNameContainingIgnoreCase(@Param("name") String name);
}

