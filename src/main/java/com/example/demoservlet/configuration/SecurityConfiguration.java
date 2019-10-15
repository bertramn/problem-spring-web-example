package com.example.demoservlet.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

/**
 * Configures the spring security subsystem in integrates it with problem support.
 */
@Configuration
@EnableWebSecurity(debug = true /* FIXME turn this off in production */)
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class) // disable white label error page
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final SecurityProblemSupport problemSupport;

  @Autowired
  public SecurityConfiguration(SecurityProblemSupport problemSupport) {
    this.problemSupport = problemSupport;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void configure(HttpSecurity http) throws Exception {

    http
      // disable useless defaults
      .csrf().disable()
      .logout().disable()
      .requestCache().disable()
      // enable problem support
      .exceptionHandling()
      .authenticationEntryPoint(problemSupport)
      .accessDeniedHandler(problemSupport).and()
      // APIs never hold any session state, security related or otherwise
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      // configure security exclusions for API
      .authorizeRequests()
      .regexMatchers(HttpMethod.GET, "/persons/?.*")
      .permitAll()
      // all other requests are to be secured
      .anyRequest()
      .fullyAuthenticated();

    // remove other useless security configurers added by default
    http.removeConfigurers(DefaultLoginPageConfigurer.class);

  }

}
