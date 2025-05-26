package org.bebriki.gateway.presentation.dto.registration.mappers;

import org.bebriki.gateway.infrastructure.users.entities.UserEntity;
import org.bebriki.gateway.presentation.dto.registration.RegistrateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class RegistrateUserRequestMapper {
    public UserEntity fromRequest(RegistrateUserRequest request) {
        return UserEntity.builder()
                .login(request.getUsername())
                .password(request.getPassword())
                .userRole(request.getRole())
                .build();
    }
}