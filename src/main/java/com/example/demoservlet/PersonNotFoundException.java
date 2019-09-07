package com.example.demoservlet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {

  public PersonNotFoundException(String name) {
    super(name + " not found");
  }

}
