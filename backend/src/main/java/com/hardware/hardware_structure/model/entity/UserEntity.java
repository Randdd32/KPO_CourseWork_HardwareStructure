package com.hardware.hardware_structure.model.entity;

import com.hardware.hardware_structure.model.enums.UserRole;
import com.hardware.hardware_structure.model.authentication.RefreshTokenEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserEntity extends BaseEntity {
    @Check(constraints = "length(email) >= 1")
    @Column(nullable = false, unique = true, columnDefinition = "text")
    private String email;

    @Check(constraints = "length(password) >= 8")
    @Column(nullable = false, length = 60)
    private String password;

    @Check(constraints = "length(phone_number) >= 1")
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToOne
    @JoinColumn(name = "fk_id_employee", nullable = false)
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private Set<RefreshTokenEntity> tokens = new HashSet<>();

    public UserEntity(String email, String password, String phoneNumber, UserRole role, EmployeeEntity employee) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, email, password, phoneNumber, role, Optional.ofNullable(employee).map(EmployeeEntity::getId).orElse(null)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final UserEntity other = (UserEntity) obj;

        return Objects.equals(this.id, other.id)
                && Objects.equals(this.email, other.email)
                && Objects.equals(this.password, other.password)
                && Objects.equals(this.phoneNumber, other.phoneNumber)
                && this.role == other.role
                && Objects.equals(
                Optional.ofNullable(this.employee).map(EmployeeEntity::getId).orElse(null),
                Optional.ofNullable(other.employee).map(EmployeeEntity::getId).orElse(null));
    }
}
