package org.bebriki.gateway.application.users.services;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.infrastructure.users.entities.UserEntity;
import org.bebriki.gateway.infrastructure.users.entities.properties.mappers.RoleMapper;
import org.bebriki.gateway.infrastructure.users.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with login '%s' not found", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                List.of(roleMapper.toSimpleGrantedAuthority(user.getUserRole()))
        );
    }
}