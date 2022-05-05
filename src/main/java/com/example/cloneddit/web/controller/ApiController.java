package com.example.cloneddit.web.controller;

import com.example.cloneddit.registration.RegisterService;
import com.example.cloneddit.registration.user.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cloneddit/api")
@RestController
@AllArgsConstructor
public class ApiController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest request){
        registerService.register(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "account-verify")
    public ResponseEntity<String> accountVerify(@RequestParam("token") String token){
        registerService.accountVerify(token);
        return new ResponseEntity<>("Successfully account activated ", HttpStatus.OK);
    }


}

