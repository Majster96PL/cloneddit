package com.example.cloneddit.api.login.jwt;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class JWTRefreshService {

    private final JWTRefreshRepository jwtRefreshRepository;

    public JWTRefresh generateToken(){
        JWTRefresh refreshToken = new JWTRefresh();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreated(Instant.now());
        return jwtRefreshRepository.save(refreshToken);
    }

    public void validateToken(String token){
        jwtRefreshRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token!"));
    }

    public void deleteToken(String token){
        jwtRefreshRepository.deleteByToken(token);
    }
}
