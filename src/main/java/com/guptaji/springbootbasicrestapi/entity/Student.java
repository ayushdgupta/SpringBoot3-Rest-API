package com.guptaji.springbootbasicrestapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

  private String firstName;
  private String lastName;
  private String className;
  @Id private int rollNo;

  public Student() {
    // default constructor
  }

  public Student(String firstName, String lastName, String className, int rollNo) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.className = className;
    this.rollNo = rollNo;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public int getRollNo() {
    return rollNo;
  }

  public void setRollNo(int rollNo) {
    this.rollNo = rollNo;
  }

  @Override
  public String toString() {
    return "Student{"
        + "firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", className='"
        + className
        + '\''
        + ", rollNo="
        + rollNo
        + '}';
  }
}
