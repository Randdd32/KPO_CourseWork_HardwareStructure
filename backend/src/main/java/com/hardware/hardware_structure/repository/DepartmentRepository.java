package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Long>, PagingAndSortingRepository<DepartmentEntity, Long> {
    Optional<DepartmentEntity> findByNameIgnoreCase(String name);

    @Query("""
    SELECT d FROM DepartmentEntity d
    WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(:name) LIKE LOWER(CONCAT('%', d.name, '%'))
    ORDER BY d.id ASC
    """)
    List<DepartmentEntity> findByNameContainingIgnoreCase(@Param("name") String name);
}
