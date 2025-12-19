package com.hardware.hardware_structure.service.authentication;

import com.hardware.hardware_structure.core.configuration.Constants;
import com.hardware.hardware_structure.model.authentication.RefreshTokenEntity;
import com.hardware.hardware_structure.model.entity.UserEntity;
import com.hardware.hardware_structure.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void createToken(UserEntity user, String newToken) {
        List<RefreshTokenEntity> existingTokens = refreshTokenRepository.findAllByUser(user);

        if (existingTokens.size() >= Constants.REFRESH_TOKEN_LIMIT) {
            refreshTokenRepository.deleteAllByUser(user);
        }

        RefreshTokenEntity newEntity = new RefreshTokenEntity();
        newEntity.setUser(user);
        newEntity.setToken(newToken);

        refreshTokenRepository.save(newEntity);
    }

    @Transactional
    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Transactional
    public void deleteAllUserTokens(UserEntity user) {
        refreshTokenRepository.deleteAllByUser(user);
    }

    @Transactional(readOnly = true)
    public RefreshTokenEntity validateAndGetToken(String token) {
        if (token == null || token.isBlank())
            throw new CredentialsExpiredException("Missing token");

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token (not found in DB)"));
    }
}
