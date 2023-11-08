package com.guptaji.springbootbasicrestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigModel {

  @JsonProperty(value = "Name")
  private String name;

  @JsonProperty(value = "SurName")
  private String surName;

  @JsonProperty(value = "Degree")
  private String degree;

  @JsonProperty(value = "College")
  private String college;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurName() {
    return surName;
  }

  public void setSurName(String surName) {
    this.surName = surName;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public String getCollege() {
    return college;
  }

  public void setCollege(String college) {
    this.college = college;
  }

  @Override
  public String toString() {
    return "ConfigModel{"
        + "name='"
        + name
        + '\''
        + ", surName='"
        + surName
        + '\''
        + ", degree='"
        + degree
        + '\''
        + ", college='"
        + college
        + '\''
        + '}';
  }
}
