package com.example.demoservlet.configuration;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.general.ProblemAdviceTrait;
import org.zalando.problem.spring.web.advice.general.ResponseStatusAdviceTrait;
import org.zalando.problem.spring.web.advice.general.ThrowableAdviceTrait;
import org.zalando.problem.spring.web.advice.general.UnsupportedOperationAdviceTrait;
import org.zalando.problem.spring.web.advice.http.HttpAdviceTrait;
import org.zalando.problem.spring.web.advice.io.IOAdviceTrait;
import org.zalando.problem.spring.web.advice.network.NetworkAdviceTrait;
import org.zalando.problem.spring.web.advice.routing.RoutingAdviceTrait;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.ValidationAdviceTrait;

/**
 * Configures all exception to problem mappings for this application. Problem is, we'd have to know all exceptions
 * thrown by our application. There is also no ability for us to "hide" some exceptions and pass on others.
 */
@ControllerAdvice
public class ApplicationExceptionHandling implements
  ProblemAdviceTrait,
  ResponseStatusAdviceTrait,
  UnsupportedOperationAdviceTrait,
  HttpAdviceTrait,
  IOAdviceTrait,
  NetworkAdviceTrait,
  RoutingAdviceTrait,
  ValidationAdviceTrait,
  SecurityAdviceTrait,
  ThrowableAdviceTrait {

  @Override
  public boolean isCausalChainsEnabled() {
    return true;
  }

}
