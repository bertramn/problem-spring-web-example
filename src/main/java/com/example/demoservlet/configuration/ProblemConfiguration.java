package com.example.demoservlet.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class ProblemConfiguration {

  /**
   * Configuration of the Problem Jackson modules must be performed before Jackson configuration and also the
   * {@link SecurityProblemSupport} component.
   */
  @Configuration
  @AutoConfigureBefore({
    JacksonAutoConfiguration.class
  })
  @ConditionalOnClass(com.fasterxml.jackson.databind.ObjectMapper.class)
  static class ProblemJacksonModuleConfiguration {

    @Bean
    @ConditionalOnClass(ProblemModule.class)
    public ProblemModule problemModule() {
      return new ProblemModule();
    }

    @Bean
    @ConditionalOnClass(ConstraintViolationProblemModule.class)
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
      return new ConstraintViolationProblemModule();
    }

  }

  /**
   * Configuration of the {@link SecurityProblemSupport} component after the Jackson mapper has been configured correctly.
   */
  @Configuration
  @AutoConfigureAfter({
    JacksonAutoConfiguration.class
  })
  @Import(SecurityProblemSupport.class)
  static class SecurityProblemSupportConfiguration {

  }

}
