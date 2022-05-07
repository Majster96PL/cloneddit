package com.example.cloneddit.api.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String authenticationToken;
    private String username;
}
