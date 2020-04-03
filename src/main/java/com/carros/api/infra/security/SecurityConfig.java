package com.carros.api.infra.security;

import com.carros.api.infra.cors.CorsConfig;
import com.carros.api.infra.security.jwt.JwtAuthenticationFilter;
import com.carros.api.infra.security.jwt.JwtAuthorizationFilter;
import com.carros.api.infra.security.jwt.handler.AccessDeniedHandler;
import com.carros.api.infra.security.jwt.handler.UnauthorizedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("userDetailsService")
  private UserDetailsService userDetailsService;

  @Autowired
  private UnauthorizedHandler unauthorizedHandler;

  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    AuthenticationManager authManager = authenticationManager();

    http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
        .authorizeRequests()
        // Authorizes request to login endpoint
        .antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
        // Authorizes configuration request endpoint
        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/actuator/**", "/error").permitAll().anyRequest()
        // Protects all the rest
        .authenticated()
        // Disable CSRF
        .and().csrf().disable()
        // Sets CORS config
        .addFilter(new CorsConfig())
        // Sets filter
        .addFilter(new JwtAuthenticationFilter(authManager))
        .addFilter(new JwtAuthorizationFilter(authManager, userDetailsService))
        // Sets exception handling
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(unauthorizedHandler)
        // Desactivates Cookie
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
  }

}
