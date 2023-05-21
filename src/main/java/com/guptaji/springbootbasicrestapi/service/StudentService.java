package com.guptaji.springbootbasicrestapi.service;

import com.guptaji.springbootbasicrestapi.entity.Student;

import java.util.List;

public interface StudentService {

  List<Student> getAllStudentData();

  Student addNewStudentData(Student student);

  Student getStudentByRoll(int roll);

  Boolean updateStudentData(Student student);

  Boolean deleteStudentData(int roll);

  List<Student> fetchAllStudentUsingJPQL();

  List<Student> fetchAllStudentUsingFirstAndLastName(String firstName, String lastName);

  List<Student> fetchAllStudentUsingNative();

  List<Student> updateStudentLastName(String firstName, String lastName);

  List<Student> findStudentsByFirstName(String firstName);

  List<Student> findStudentsByFirstAndLastName(String firstName, String lastName);
}
