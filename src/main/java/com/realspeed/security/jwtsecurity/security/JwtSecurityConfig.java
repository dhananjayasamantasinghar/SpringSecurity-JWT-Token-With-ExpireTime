package com.realspeed.security.jwtsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/api/**"));
	private AuthenticationProvider provider;

	public JwtSecurityConfig(final JwtAuthenticationProvider authenticationProvider) {
		super();
		this.provider = authenticationProvider;
	}

	@Override
    public void configure(final WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/token");
    }
	
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter(PROTECTED_URLS);
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new DefaultJwtSuccessHandler());
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf()
			.disable()
			.formLogin().disable()
			.httpBasic().disable()
			.logout().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.and()
			.authenticationProvider(provider)
			.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests().requestMatchers(PROTECTED_URLS)
			.authenticated()
			.and()
			.headers()
			.cacheControl();
	}

	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}
}
