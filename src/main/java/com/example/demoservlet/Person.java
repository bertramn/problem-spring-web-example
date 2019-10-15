package com.example.demoservlet;

public class Person {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Person withName(String name) {
    setName(name);
    return this;
  }
}
