package com.example.cloneddit.web.controller;

import com.example.cloneddit.api.ApiService;
import com.example.cloneddit.api.login.LoginRequest;
import com.example.cloneddit.api.login.LoginResponse;
import com.example.cloneddit.api.registration.user.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@RequestMapping(path = "/cloneddit/api")
@RestController
@AllArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRequest request){
         return apiService.register(request);
    }

    @GetMapping(path = "/register/account-verify")
    public String accountVerify(@RequestParam("token") String token){
        return apiService.confirmToken(token);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return apiService.login(loginRequest);
    }


}

