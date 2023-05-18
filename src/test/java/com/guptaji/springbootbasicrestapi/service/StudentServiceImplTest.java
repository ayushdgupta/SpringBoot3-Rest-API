package com.guptaji.springbootbasicrestapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.guptaji.springbootbasicrestapi.entity.Student;
import com.guptaji.springbootbasicrestapi.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
  @Disabled
  void addNewStudentData() {}

  @Test
  @Disabled
  void getStudentByRoll() {}

  @Test
  @Disabled
  void updateStudentData() {}

  @Test
  @Disabled
  void deleteStudentData() {}
}
