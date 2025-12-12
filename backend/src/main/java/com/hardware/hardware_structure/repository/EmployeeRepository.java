package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long>, PagingAndSortingRepository<EmployeeEntity, Long> {
    @Query("SELECT e FROM EmployeeEntity e WHERE " +
            "LOWER(CONCAT(e.lastName, ' ', e.firstName, ' ', COALESCE(e.patronymic, ''))) LIKE LOWER(CONCAT('%', :fullNamePart, '%'))" +
            "ORDER BY e.id ASC")
    List<EmployeeEntity> findByFullNameContainingIgnoreCase(@Param("fullNamePart") String fullNamePart);

    @Query("SELECT e FROM EmployeeEntity e WHERE " +
            "LOWER(CONCAT(e.lastName, ' ', e.firstName, ' ', COALESCE(e.patronymic, ''))) LIKE LOWER(CONCAT('%', :fullNamePart, '%'))" +
            "ORDER BY e.id ASC")
    Page<EmployeeEntity> findByFullNameContainingIgnoreCase(@Param("fullNamePart") String fullNamePart, Pageable pageable);

    @Query("""
    SELECT e FROM EmployeeEntity e
    WHERE (:fullNamePart IS NULL OR LOWER(CONCAT(e.lastName, ' ', e.firstName, ' ', COALESCE(e.patronymic, ''))) LIKE %:fullNamePart%)
    AND (
        :withoutAccount IS NULL
        OR (
            :withoutAccount = true AND NOT EXISTS (
                SELECT u FROM UserEntity u WHERE u.employee.id = e.id
            )
        )
        OR (
            :withoutAccount = false AND EXISTS (
                SELECT u FROM UserEntity u WHERE u.employee.id = e.id
            )
        )
    )
    ORDER BY e.id ASC
    """)
    Page<EmployeeEntity> findByFullNameAndWithoutAccount(@Param("fullNamePart") String fullNamePart,
                                                         @Param("withoutAccount") Boolean withoutAccount,
                                                         Pageable pageable);
}
