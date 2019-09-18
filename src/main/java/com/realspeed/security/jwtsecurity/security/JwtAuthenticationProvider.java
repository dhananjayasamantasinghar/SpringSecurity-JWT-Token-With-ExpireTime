package com.realspeed.security.jwtsecurity.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.realspeed.security.jwtsecurity.model.AuthToken;
import com.realspeed.security.jwtsecurity.model.JwtUser;
import com.realspeed.security.jwtsecurity.model.JwtUserDetails;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, 
    		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) 
    				throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, 
    		UsernamePasswordAuthenticationToken authToken) throws AuthenticationException {

        String token = ((AuthToken) authToken).getToken();
        JwtUser jwtUser = jwtUtil.validate(token);

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwtUser.getRole());
       
        return new JwtUserDetails(jwtUser.getSubject(), jwtUser.getId(),token,grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (AuthToken.class.isAssignableFrom(aClass));
    }
}
