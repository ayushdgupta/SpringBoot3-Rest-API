package com.guptaji.springbootbasicrestapi.service;

import com.guptaji.springbootbasicrestapi.entity.Student;

import java.util.List;

public interface StudentService {

  List<Student> getAllStudentData();

  Student addNewStudentData(Student student);

  Student getStudentByRoll(int roll);

  Boolean updateStudentData(Student student);

  Boolean deleteStudentData(int roll);
}
