package org.bebriki.gateway.infrastructure.users.entities.properties.mappers;

import org.bebriki.gateway.infrastructure.users.entities.properties.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public SimpleGrantedAuthority toSimpleGrantedAuthority(UserRole role) {
        return switch (role) {
            case ADMIN -> new SimpleGrantedAuthority("ROLE_ADMIN");
            case CLIENT -> new SimpleGrantedAuthority("ROLE_CLIENT");
        };
    }
}