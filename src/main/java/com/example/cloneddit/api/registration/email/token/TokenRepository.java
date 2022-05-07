package com.example.cloneddit.api.registration.email.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.confirmedDate = ?2 WHERE t.token = ?1")
    int updateToken(String token, LocalDateTime confirmedDate);
}
