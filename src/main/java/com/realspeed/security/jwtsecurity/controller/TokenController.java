package com.realspeed.security.jwtsecurity.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realspeed.security.jwtsecurity.model.JwtUser;
import com.realspeed.security.jwtsecurity.security.JwtUtil;

@RestController
@RequestMapping("/token")
@CrossOrigin
public class TokenController {


    private JwtUtil jwtGenerator;

    public TokenController(JwtUtil jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping
    public String generate(@RequestBody final JwtUser jwtUser) {
        return jwtGenerator.generate(jwtUser);

    }
}
