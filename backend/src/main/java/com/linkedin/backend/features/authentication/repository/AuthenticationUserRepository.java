package com.linkedin.backend.features.authentication.repository;

import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthenticationUserRepository extends JpaRepository<AuthenticationUser,Long> {
    Optional<AuthenticationUser> findByEmail(String email);
}
