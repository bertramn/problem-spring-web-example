package com.example.demoservlet;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
public class PersonService {

  @GetMapping("/persons/{name}")
  public Person getPersonByName(@PathVariable("name") String name) {
    if (name == null || "".equals(name.trim())) {
      throw new PersonNotFoundException("empty");
    } else if ("Fred".equals(name)) {
      throw new PersonNotFoundException(name);
    } else if ("Illegal".equals(name)) {
      throw new IllegalArgumentException("some internal error");
    } else if ("Runtime".equals(name)) {
      throw new IllegalArgumentException("some internal runtime error");
    }
    return new Person().withName(name);
  }

  @Secured("ROLE_ADMIN")
  @PostMapping("/persons")
  public ResponseEntity createPerson() {
    URI location = URI.create("/persons/" + UUID.randomUUID().toString());
    return ResponseEntity.created(location).build();
  }

}
