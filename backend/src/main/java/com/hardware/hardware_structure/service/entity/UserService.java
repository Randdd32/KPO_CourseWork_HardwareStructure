package com.hardware.hardware_structure.service.entity;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.core.error.NotFoundException;
import com.hardware.hardware_structure.core.utility.ValidationUtils;
import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractEntityService<UserEntity> {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserEntity> getAll() {
        return StreamSupport.stream(repository.findAll(Sort.by(Sort.Direction.ASC, "id")).spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public Page<UserEntity> getAll(int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return repository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public UserEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, id));
    }

    @Transactional(readOnly = true)
    public UserEntity getByEmail(String email) {
        return repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
    }

    @Transactional
    public UserEntity create(UserEntity entity) {
        validate(entity, null);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity);
    }

    @Transactional
    public UserEntity update(long id, UserEntity entity) {
        validate(entity, id);
        final UserEntity existsEntity = get(id);
        existsEntity.setEmail(entity.getEmail());
        if (entity.getPassword() != null && !entity.getPassword().isBlank()) {
            existsEntity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        existsEntity.setPhoneNumber(entity.getPhoneNumber());
        existsEntity.setRole(entity.getRole());
        existsEntity.setEmployee(entity.getEmployee());
        return repository.save(existsEntity);
    }

    @Transactional
    public UserEntity delete(long id) {
        final UserEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    protected void validate(UserEntity entity, Long id) {
        if (entity == null) {
            throw new IllegalArgumentException("User entity is null");
        }
        validateStringField(entity.getEmail(), "User email");
        ValidationUtils.validateEmailFormat(entity.getEmail());
        if (entity.getPassword() != null && !entity.getPassword().isBlank() && !entity.getPassword().matches(Constants.PASSWORD_PATTERN)) {
            throw new IllegalArgumentException("Password has invalid format: " + entity.getPassword());
        }
        validateStringField(entity.getPhoneNumber(), "User phone number");
        entity.setPhoneNumber(normalizePhoneNumber(entity.getPhoneNumber()));
        if (entity.getRole() == null) {
            throw new IllegalArgumentException("User role must not be null");
        }
        if (entity.getEmployee() == null) {
            throw new IllegalArgumentException("User employee must not be null");
        }

        final Optional<UserEntity> existingUser = repository.findByEmailIgnoreCase(entity.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    String.format("User with email %s already exists", entity.getEmail())
            );
        }
    }

    private String normalizePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches(Constants.PHONE_PATTERN)) {
            throw new IllegalArgumentException("Phone number has invalid format: " + phoneNumber);
        }
        String cleaned = phoneNumber.replaceAll("[\\s\\-()]", "");
        if (cleaned.startsWith("8")) {
            cleaned = "+7" + cleaned.substring(1);
        } else if (!cleaned.startsWith("+")) {
            throw new IllegalArgumentException("Phone number must start with +country code or 8");
        }
        return cleaned;
    }
}
