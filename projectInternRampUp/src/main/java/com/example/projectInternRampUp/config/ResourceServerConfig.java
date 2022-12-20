package com.example.projectInternRampUp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/users").hasRole("ADMIN") //Correct
                .antMatchers(HttpMethod.POST, "/orders", "/customers").authenticated()
                .antMatchers(HttpMethod.DELETE, "/orders/**", "/customers/**", "/users/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/orders/**", "/customers/**", "/users/**").authenticated()
                .antMatchers("/users/{id}", "/customers/{id}", "/orders/{id}").authenticated()
                .antMatchers("/users/**", "/customers/**", "/roles/**", "/orders/**").hasRole("ADMIN")
                .antMatchers("/productOfferings/**", "/characteristics/**").authenticated()
                .anyRequest().denyAll();
    }
}