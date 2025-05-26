package org.bebriki.gateway.presentation.dto.registration;

import lombok.Data;
import org.bebriki.gateway.infrastructure.users.entities.properties.UserRole;

@Data
public class RegistrateUserRequest {
    private final String username;
    private final String password;
    private final UserRole role;
}
