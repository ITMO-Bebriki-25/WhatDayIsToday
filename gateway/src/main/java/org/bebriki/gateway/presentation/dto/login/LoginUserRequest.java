package org.bebriki.gateway.presentation.dto.login;

import lombok.Data;

@Data
public class LoginUserRequest {
    private final String username;
    private final String password;
}