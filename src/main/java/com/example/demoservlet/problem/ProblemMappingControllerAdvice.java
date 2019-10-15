package com.example.demoservlet.problem;

import com.example.demoservlet.PersonNotFoundException;
import org.apiguardian.api.API;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.AdviceTrait;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.zalando.problem.Status.*;

/**
 * Configures mapping of all exception to problem definitions.
 */
@ControllerAdvice
public class ProblemMappingControllerAdvice implements AdviceTrait {

  @API(status = INTERNAL)
  @ExceptionHandler
  public ResponseEntity<Problem> handleThrowable(final Throwable e, final NativeWebRequest request) {
    // TODO this is where we should map exceptions to problem responses
    if (e instanceof PersonNotFoundException) {
      return create(NOT_FOUND, e, request);
    } else if (e instanceof AccessDeniedException || e instanceof InsufficientAuthenticationException) {
      return create(UNAUTHORIZED, e, request);
    } else if (e instanceof IllegalArgumentException) {
      return create(BAD_REQUEST, e, request);
    } else {
      return create(INTERNAL_SERVER_ERROR, e, request);
    }
  }

  @Override
  public boolean isCausalChainsEnabled() {
    return true;
  }

}
