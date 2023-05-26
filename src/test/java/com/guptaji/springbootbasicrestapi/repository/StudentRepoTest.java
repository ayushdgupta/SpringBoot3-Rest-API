package com.guptaji.springbootbasicrestapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.guptaji.springbootbasicrestapi.entity.Student;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.persistence.EntityManager;

/*
   here we don't need '@AutoConfigureTestDatabase' because @DataJpaTest already contains that annotation.
*/

// @AutoConfigureTestDatabase
@DataJpaTest
class StudentRepoTest {

  private Student student;
  private Student studentTwo;

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private EntityManager entityManager;
  @Autowired private StudentRepo studentRepo;

  @BeforeEach
  public void init() {
    student = new Student("Naruto", "Uzumaki", "Hokage", 7);
    studentTwo = new Student("Sasuke", "Uchiha", "Shadow Hokage", 8);
  }

  @Test
  void injectedComponentsAreNotNull() {
    assertNotNull(dataSource);
    assertNotNull(jdbcTemplate);
    assertNotNull(entityManager);
    assertNotNull(studentRepo);
  }

  @Test
  void testSaveAndFindByIdMethod() {
    Student savedStudentData = studentRepo.save(student);

    assertNotNull(savedStudentData);
    assertEquals(7, savedStudentData.getRollNo());
    assertEquals(savedStudentData, studentRepo.findById(student.getRollNo()).get());
  }

  @Test
  void testFindAllMethod() {
    Student savedStudentData = studentRepo.save(student);
    Student savedStudentDataTwo = studentRepo.save(studentTwo);

    List<Student> studentListData = studentRepo.findAll();

    assertNotNull(studentListData);
    assertEquals(2, studentListData.size());
  }

  @Test
  void testDeleteMethod() {
    Student savedStudentData = studentRepo.save(student);

    studentRepo.deleteById(student.getRollNo());

    Optional<Student> studentOptional = studentRepo.findById(student.getRollNo());

    assertTrue(studentOptional.isEmpty());
  }

  @Test
  void getAllStudentsUsingJPQL() {
    Student savedStudentData = studentRepo.save(student);
    Student savedStudentDataTwo = studentRepo.save(studentTwo);

    List<Student> studentListData = studentRepo.getAllStudentsUsingJPQL();

    assertNotNull(studentListData);
    assertEquals(2, studentListData.size());
  }

  @Test
  void getAllStudentsUsingFirstAndLastName() {
    Student savedStudentDataTwo = studentRepo.save(studentTwo);

    List<Student> studentListData =
        studentRepo.getAllStudentsUsingFirstAndLastName(
            studentTwo.getFirstName(), studentTwo.getLastName());

    assertNotNull(studentListData);
    assertEquals(1, studentListData.size());
  }

  // This test case was not working directly due to some spring internal issues but when we add
  // 'clearAutomatically = true' in @Modifying annotation in actual method then this works fine.
  // Issue was that even after updating the lastName DB was automatically picking the older last
  // name
  // but in actual working it was working fine, In JUnit only it was creating issues.
  @Test
  void updateLastNameUsingFirstByJPQL() {
    Student savedStudentData = studentRepo.save(student);

    studentRepo.updateLastNameUsingFirstByJPQL(student.getFirstName(), studentTwo.getLastName());

    List<Student> studentListData = studentRepo.findByFirstName(student.getFirstName());

    assertEquals(1, studentListData.size());
    assertEquals(studentTwo.getLastName(), studentListData.get(0).getLastName());
  }

  @Test
  void getAllStudentsUsingNative() {

    Student savedStudentData = studentRepo.save(student);
    Student savedStudentDataTwo = studentRepo.save(studentTwo);

    List<Student> studentListData = studentRepo.getAllStudentsUsingNative();

    assertNotNull(studentListData);
    assertEquals(2, studentListData.size());
  }

  @Test
  void findByFirstName() {
    Student savedStudentDataTwo = studentRepo.save(studentTwo);

    List<Student> studentListData = studentRepo.findByFirstName(studentTwo.getFirstName());

    assertNotNull(studentListData);
    assertEquals(1, studentListData.size());
  }

  @Test
  void findByFirstNameOrLastName() {
    Student savedStudentDataTwo = studentRepo.save(studentTwo);

    List<Student> studentListData =
        studentRepo.findByFirstNameOrLastName(studentTwo.getFirstName(), "ABCD");

    assertNotNull(studentListData);
    assertEquals(1, studentListData.size());
  }
}
