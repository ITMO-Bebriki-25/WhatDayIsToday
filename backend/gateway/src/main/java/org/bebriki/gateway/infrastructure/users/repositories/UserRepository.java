package org.bebriki.gateway.infrastructure.users.repositories;

import org.bebriki.gateway.infrastructure.users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}