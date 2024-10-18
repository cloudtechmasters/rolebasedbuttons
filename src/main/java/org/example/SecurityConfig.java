package org.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for stateless APIs
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set stateless session
            .and()
            .authorizeRequests()
                .antMatchers("/dashboard", "/dashboard.html").permitAll() // Allow access to both dashboard endpoints for all roles
                .anyRequest().authenticated(); // Require authentication for other requests
    }
}
