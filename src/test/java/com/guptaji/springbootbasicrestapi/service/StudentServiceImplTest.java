package com.guptaji.springbootbasicrestapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import com.guptaji.springbootbasicrestapi.entity.Student;
import com.guptaji.springbootbasicrestapi.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

  @Mock private StudentRepo studentRepo;

  @InjectMocks private StudentServiceImpl studentService;

  private Student student;
  private List<Student> studentList;

  @BeforeEach
  public void init() {
    student = new Student("Naruto", "Uzumaki", "Hokage", 7);
    studentList = new ArrayList<>();
    studentList.add(student);
  }

  @Test
  void getAllStudentData() {
    given(studentRepo.findAll()).willReturn(studentList);

    List<Student> allStudentData = studentService.getAllStudentData();

    assertNotNull(allStudentData);
    assertEquals(studentList.size(), allStudentData.size());
    assertEquals(studentList.get(0).getFirstName(), allStudentData.get(0).getFirstName());
  }

  @Test
  void addNewStudentData() {
    given(studentRepo.save(ArgumentMatchers.any(Student.class))).willReturn(student);

    Student newStudentData = studentService.addNewStudentData(student);

    assertNotNull(newStudentData);
    assertEquals(newStudentData.getRollNo(), student.getRollNo());
    assertEquals(student.getFirstName(), newStudentData.getFirstName());
  }

  @Test
  void getStudentByRoll() {
    given(studentRepo.findById(ArgumentMatchers.any(Integer.class)))
        .willReturn(Optional.of(student));

    Student studentData = studentService.getStudentByRoll(1);

    assertNotNull(studentData);
    assertEquals(studentData.getRollNo(), student.getRollNo());
    assertEquals(student.getFirstName(), studentData.getFirstName());
  }

  @Test
  void updateStudentData() {
    given(studentRepo.save(ArgumentMatchers.any(Student.class))).willReturn(student);

    boolean updatedStudentData = studentService.updateStudentData(student);

    //    assertEquals(true, updatedStudentData);
    assertTrue(updatedStudentData);
  }

  @Test
  void deleteStudentData() {
    // Here we can see that studentRepo.deleteById() is not returning anything in our original
    // method, so it's mocking can be done with the help of willDoNothing().
    willDoNothing().given(studentRepo).deleteById(ArgumentMatchers.any(Integer.class));

    given(studentRepo.findById(ArgumentMatchers.any(Integer.class))).willReturn(Optional.empty());

    boolean result = studentService.deleteStudentData(1);

    assertTrue(result);
  }
}
