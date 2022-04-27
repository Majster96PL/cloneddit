package com.example.cloneddit.web.controller;

import com.example.cloneddit.registration.RegisterService;
import com.example.cloneddit.registration.user.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class ApiController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserRequest request){
        registerService.register(request);
        return new ResponseEntity(HttpStatus.OK);
    }


}

