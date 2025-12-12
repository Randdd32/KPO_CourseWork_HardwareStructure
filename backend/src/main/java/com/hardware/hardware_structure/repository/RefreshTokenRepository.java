package com.hardware.hardware_structure.repository;

import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.model.authentication.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    List<RefreshTokenEntity> findAllByUser(UserEntity user);

    void deleteAllByUser(UserEntity user);

    void deleteByToken(String token);
}
