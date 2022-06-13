package com.example.cloneddit.api.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expire;
    private String username;
}
