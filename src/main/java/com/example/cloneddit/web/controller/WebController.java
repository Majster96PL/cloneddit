package com.example.cloneddit.web.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
public class WebController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
