package org.bebriki.gateway.presentation.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.application.users.contracts.AuthService;
import org.bebriki.gateway.application.users.exceptions.UserCreateException;
import org.bebriki.gateway.infrastructure.users.entities.UserEntity;
import org.bebriki.gateway.presentation.dto.login.LoginUserRequest;
import org.bebriki.gateway.presentation.dto.login.LoginUserResponse;
import org.bebriki.gateway.presentation.dto.login.mappers.LoginUserRequestMapper;
import org.bebriki.gateway.presentation.dto.registration.RegistrateUserRequest;
import org.bebriki.gateway.presentation.dto.registration.mappers.RegistrateUserRequestMapper;
import org.bebriki.gateway.utils.jwt.TokenDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthService authService;
    private final LoginUserRequestMapper loginUserRequestMapper;
    private final RegistrateUserRequestMapper registrateUserRequestMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginRequest) {
        TokenDto tokenDto;

        try {
            tokenDto = authService.loginUser(loginUserRequestMapper.fromRequest(loginRequest));
        }
        catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }

        return ResponseEntity.ok(new LoginUserResponse(tokenDto.token(), tokenDto.expires()));
    }

    @PostMapping("/registration")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@RequestBody RegistrateUserRequest registrationRequest) {
        UserEntity user;

        try {
            user = authService.createUser(registrateUserRequestMapper.fromRequest(registrationRequest));
        }
        catch (UserCreateException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Registration successful. Login: %s", user.getLogin()));
    }
}