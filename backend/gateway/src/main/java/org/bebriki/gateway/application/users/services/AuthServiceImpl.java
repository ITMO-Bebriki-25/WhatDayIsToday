package org.bebriki.gateway.application.users.services;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.application.users.contracts.AuthService;
import org.bebriki.gateway.application.users.exceptions.UserCreateException;
import org.bebriki.gateway.infrastructure.users.entities.UserEntity;
import org.bebriki.gateway.infrastructure.users.repositories.UserRepository;
import org.bebriki.gateway.utils.jwt.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity createUser(UserEntity user) throws UserCreateException {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new UserCreateException(
                    String.format("User with login '%s' already exists", user.getLogin()
                    ));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String loginUser(UserEntity user) throws BadCredentialsException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getLogin(),
                        user.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());

        return jwtTokenUtils.generateToken(userDetails);
    }
}