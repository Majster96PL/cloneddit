package com.example.cloneddit.api.registration.email.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveToken(Token token){
        tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token){
        return tokenRepository.findByToken(token);
    }

    public int setConfirmedToken(String token){
        return tokenRepository.updateToken(token, LocalDateTime.now());
    }
}
