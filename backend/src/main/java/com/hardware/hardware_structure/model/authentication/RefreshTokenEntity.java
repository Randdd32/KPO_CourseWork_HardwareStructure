package com.hardware.hardware_structure.model.authentication;

import com.hardware.hardware_structure.model.entity.BaseEntity;
import com.hardware.hardware_structure.model.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, unique = true)
    private String token;

    @Override
    public int hashCode() {
        return Objects.hash(
                id, token, Optional.ofNullable(user).map(UserEntity::getId).orElse(null)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final RefreshTokenEntity other = (RefreshTokenEntity) obj;

        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.token, other.token)
                && Objects.equals(
                Optional.ofNullable(this.user).map(UserEntity::getId).orElse(null),
                Optional.ofNullable(other.user).map(UserEntity::getId).orElse(null));
    }
}
