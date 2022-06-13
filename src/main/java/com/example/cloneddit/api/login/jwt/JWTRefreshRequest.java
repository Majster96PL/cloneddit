package com.example.cloneddit.api.login.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTRefreshRequest {
    @NotBlank
    private String refreshToken;
    private String username;
}
