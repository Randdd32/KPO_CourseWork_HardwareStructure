package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.BuildingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends CrudRepository<BuildingEntity, Long>, PagingAndSortingRepository<BuildingEntity, Long> {
    Optional<BuildingEntity> findByNameIgnoreCase(String name);

    @Query("""
    SELECT b FROM BuildingEntity b
    WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', b.name, '%'))
    ORDER BY b.id ASC
    """)
    List<BuildingEntity> findByNameContainingIgnoreCase(@Param("name") String name);
}
