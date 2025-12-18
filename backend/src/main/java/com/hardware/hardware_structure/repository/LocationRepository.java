package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<LocationEntity, Long>, PagingAndSortingRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByNameIgnoreCase(String name);

    @Query("""
    SELECT l FROM LocationEntity l
    WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', l.name, '%'))
    ORDER BY l.id ASC
    """)
    List<LocationEntity> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("""
    SELECT l FROM LocationEntity l
    WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', l.name, '%'))
    ORDER BY l.id ASC
    """)
    Page<LocationEntity> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
