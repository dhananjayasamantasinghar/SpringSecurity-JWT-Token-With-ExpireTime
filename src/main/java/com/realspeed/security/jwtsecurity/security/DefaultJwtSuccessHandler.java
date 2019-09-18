package com.realspeed.security.jwtsecurity.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class DefaultJwtSuccessHandler implements AuthenticationSuccessHandler{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, 
    		HttpServletResponse httpServletResponse, 
    		Authentication authentication) throws IOException, ServletException {
        System.out.println("Successfully Authentication");
    }
}
