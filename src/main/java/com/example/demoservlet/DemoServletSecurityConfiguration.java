package com.example.demoservlet;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Order(101)
@Configuration
@EnableWebSecurity(debug = true)
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class) // disable white label error page
@EnableGlobalMethodSecurity(securedEnabled = true)
public class DemoServletSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  @SuppressWarnings("unchecked")
  protected void configure(HttpSecurity http) throws Exception {
    http
        // disable senseless defaults
        .csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .logout().disable()
        // APIs never hold any session state, security related or otherwise
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        // TODO configure security exclusions for Actuator
        // configure security exclusions for API
        .authorizeRequests()
        .regexMatchers(HttpMethod.GET, "/persons/?.*")
        .permitAll()
        // all other requests are to be secured
        .anyRequest()
        .fullyAuthenticated();

        http.removeConfigurers(DefaultLoginPageConfigurer.class);

  }

}
