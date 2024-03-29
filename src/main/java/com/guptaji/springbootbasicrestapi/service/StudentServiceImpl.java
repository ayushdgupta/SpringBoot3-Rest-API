package com.guptaji.springbootbasicrestapi.service;

import com.guptaji.springbootbasicrestapi.entity.Student;
import com.guptaji.springbootbasicrestapi.repository.StudentRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

  @Autowired public StudentRepo studentRepo;

  @Override
  public List<Student> getAllStudentData() {
    return studentRepo.findAll();
  }

  @Override
  public Student addNewStudentData(Student student) {
    return studentRepo.save(student);
  }

  @Override
  public Student getStudentByRoll(int roll) {
    Optional<Student> studentOptional = studentRepo.findById(roll);

    // M1->
    //    if (studentOptional.isPresent()) {
    //      return studentOptional.get();
    //    } else {
    //      return null;
    //    }
    return studentOptional.orElse(null);
  }

  @Override
  public Boolean updateStudentData(Student student) {
    Student studentData = studentRepo.save(student);
    return true;
  }

  @Override
  public Boolean deleteStudentData(int roll) {
    studentRepo.deleteById(roll);
    Optional<Student> studentDataById = studentRepo.findById(roll);

    // M1->
    //    if (studentDataById.isPresent()) {
    //      return false;
    //    }
    //    return true;

    return studentDataById.isEmpty();
  }

  @Override
  public List<Student> fetchAllStudentUsingJPQL() {
    return studentRepo.getAllStudentsUsingJPQL();
  }

  @Override
  public List<Student> fetchAllStudentUsingFirstAndLastName(String firstName, String lastName) {
    return studentRepo.getAllStudentsUsingFirstAndLastName(firstName, lastName);
  }

  @Override
  public List<Student> fetchAllStudentUsingNative() {
    return studentRepo.getAllStudentsUsingNative();
  }

  @Override
  public List<Student> updateStudentLastName(String firstName, String lastName) {
    studentRepo.updateLastNameUsingFirstByJPQL(firstName, lastName);
    return studentRepo.getAllStudentsUsingJPQL();
  }

  @Override
  public List<Student> findStudentsByFirstName(String firstName) {
    return studentRepo.findByFirstName(firstName);
  }

  @Override
  public List<Student> findStudentsByFirstAndLastName(String firstName, String lastName) {
    return studentRepo.findByFirstNameOrLastName(firstName, lastName);
  }
}
