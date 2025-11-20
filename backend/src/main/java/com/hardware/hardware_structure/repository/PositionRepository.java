package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.PositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends CrudRepository<PositionEntity, Long>, PagingAndSortingRepository<PositionEntity, Long> {
    Optional<PositionEntity> findByNameIgnoreCase(String name);

    @Query("""
    SELECT p FROM PositionEntity p
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', p.name, '%'))
    ORDER BY p.id ASC
    """)
    List<PositionEntity> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("""
    SELECT p FROM PositionEntity p
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', p.name, '%'))
    ORDER BY p.id ASC
    """)
    Page<PositionEntity> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
