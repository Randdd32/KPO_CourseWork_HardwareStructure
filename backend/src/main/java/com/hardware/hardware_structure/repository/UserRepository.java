package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.employee.id = :employeeId")
    Optional<UserEntity> findByEmployeeId(@Param("employeeId") Long employeeId);
}
