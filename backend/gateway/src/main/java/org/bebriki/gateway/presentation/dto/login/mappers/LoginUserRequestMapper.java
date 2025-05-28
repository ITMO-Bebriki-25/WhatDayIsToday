package org.bebriki.gateway.presentation.dto.login.mappers;

import org.bebriki.gateway.infrastructure.users.entities.UserEntity;
import org.bebriki.gateway.presentation.dto.login.LoginUserRequest;
import org.springframework.stereotype.Component;

@Component
public class LoginUserRequestMapper {
    public UserEntity fromRequest(LoginUserRequest loginUserRequest) {
        return UserEntity.builder()
                .login(loginUserRequest.username())
                .password(loginUserRequest.password())
                .build();
    }
}