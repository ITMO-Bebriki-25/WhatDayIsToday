package org.bebriki.gateway.application.users.contracts;

import org.bebriki.gateway.utils.jwt.TokenDto;
import org.bebriki.gateway.application.users.exceptions.UserCreateException;
import org.bebriki.gateway.infrastructure.users.entities.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthService {
    UserEntity createUser(UserEntity user) throws UserCreateException;
    TokenDto loginUser(UserEntity user) throws BadCredentialsException;
}
