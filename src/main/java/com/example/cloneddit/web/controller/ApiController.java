package com.example.cloneddit.web.controller;

import com.example.cloneddit.registration.RegisterService;
import com.example.cloneddit.registration.user.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/cloneddit/api")
@RestController
@AllArgsConstructor
public class ApiController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRequest request){
         return registerService.register(request);
    }

    @GetMapping(path = "/register/account-verify")
    public String accountVerify(@RequestParam("token") String token){
        return registerService.confirmToken(token);
    }


}

