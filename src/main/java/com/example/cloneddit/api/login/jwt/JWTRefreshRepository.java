package com.example.cloneddit.api.login.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JWTRefreshRepository extends JpaRepository<JWTRefresh, Long> {
    Optional<JWTRefresh> findByToken(String token);
    void deleteByToken(String token);
}
