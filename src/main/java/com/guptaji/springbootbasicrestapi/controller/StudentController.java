package com.guptaji.springbootbasicrestapi.controller;

import com.guptaji.springbootbasicrestapi.entity.Student;
import com.guptaji.springbootbasicrestapi.service.StudentService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

  Logger LOG = LogManager.getLogger(StudentController.class);

  @Autowired public StudentService studentService;

  @GetMapping
  public ResponseEntity<?> fetchAllStudents() {
    LOG.info("Hit fetchAllStudents API");
    List<Student> studentList = studentService.getAllStudentData();
    LOG.info("fetch the data from DB");

    //    there are two ways to return the employee data with ResponseEntity
    //    M1
    return new ResponseEntity<>(studentList, HttpStatus.OK);

    //    M2
    // return ResponseEntity.status(HttpStatus.OK).body(studentList);
  }

  @PostMapping
  public ResponseEntity<?> insertNewStudent(@RequestBody Student student) {
    LOG.info("Hit insertNewStudent API");
    Student studentSavedInstance = studentService.addNewStudentData(student);
    LOG.info("Save the data in db");
    return new ResponseEntity<>(studentSavedInstance, HttpStatus.CREATED);
  }

  // Path Variable usage
  // url = http://localhost:9090/student/2
  @GetMapping("/{rollNo}")
  public ResponseEntity<?> getStudentByRollNo(@PathVariable("rollNo") int roll) {
    LOG.info("Hit getStudentByRollNo API");
    Student studentData = studentService.getStudentByRoll(roll);
    if (studentData == null) {
      LOG.error("Data corresponding to id {} not found", roll);
      return new ResponseEntity<>("Data Not Found", HttpStatus.NOT_FOUND);
    }
    LOG.info("Found the data for the id {}", roll);
    return new ResponseEntity<>(studentData, HttpStatus.FOUND);
  }

  // Query variable usage
  // url = http://localhost:9090/student/queryvariableUsage?roll=2
  @GetMapping("/queryvariableUsage")
  public ResponseEntity<?> getStudentByRollNoUsingQueryVariables(
      @RequestParam(name = "roll") int rollNo) {
    LOG.info("Hit getStudentByRollNoUsingQueryVariables API");
    Student studentData = studentService.getStudentByRoll(rollNo);
    if (studentData == null) {
      LOG.error("Data corresponding to id {} not found", rollNo);
      return new ResponseEntity<>("Data Not Found", HttpStatus.NOT_FOUND);
    }
    LOG.info("Found the data for the id {}", rollNo);
    return new ResponseEntity<>(studentData, HttpStatus.FOUND);
  }

  @PutMapping
  public ResponseEntity<?> updateStudent(@RequestBody Student student) {
    LOG.info("Update Student API Hit");
    boolean result = studentService.updateStudentData(student);
    if (result) {
      return ResponseEntity.status(HttpStatus.OK).body("Done dana done done");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Some error occured while updating the data");
    }
  }

  @DeleteMapping("/{roll}")
  public ResponseEntity<?> deleteStudent(@PathVariable("roll") int rollNo) {
    LOG.info("Delete Student API Hit");
    boolean result = studentService.deleteStudentData(rollNo);
    if (result) {
      return ResponseEntity.status(HttpStatus.OK).body("Done dana done done");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Some error occured while deleting the data");
    }
  }

  @GetMapping("/retrieveAllUsingJPQL")
  public ResponseEntity<?> retrieveAllStudentsUsingJPQL() {
    LOG.info("retrieveAllStudentsUsingJPQL API Hit");
    List<Student> studentList = studentService.fetchAllStudentUsingJPQL();
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Database is empty right now");
  }

  @GetMapping("/retrieveAllUsingFirstAndLastName")
  public ResponseEntity<?> retrieveAllStudentsUsingFirstAndLastName(
      @RequestParam("firstName") String fName, @RequestParam("lastName") String lName) {
    LOG.info("retrieveAllStudentsUsingFirstAndLastName API Hit");
    List<Student> studentList = studentService.fetchAllStudentUsingFirstAndLastName(fName, lName);
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Database is empty right now");
  }

  @GetMapping("/retrieveAllUsingNative")
  public ResponseEntity<?> retrieveAllStudentsUsingNative() {
    LOG.info("retrieveAllStudentsUsingNative API Hit");
    List<Student> studentList = studentService.fetchAllStudentUsingNative();
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Database is empty right now");
  }
}
