package com.realspeed.security.jwtsecurity.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
@CrossOrigin
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello World";
    }
}
