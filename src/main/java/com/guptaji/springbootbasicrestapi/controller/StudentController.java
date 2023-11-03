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
  public ResponseEntity<?> fetchAllStudents() throws InterruptedException {
    LOG.info("Hit fetchAllStudents API");
    // this thread.sleep() I put because I want to see if I hit this API from two places
    // then will another tomcat thread will start to process this request or request2 will wait
    // for the expiration of request1, So what happened here when I hit two request automatically
    // another thread started for 2nd request (by default by the tomcat). But when I hit three
    // request at the same time it runs two request in different thread but for third one it waits
    // for any of the other two to finish and then it starts the third one.
    Thread.sleep(20000);
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
      return new ResponseEntity<>(
          "Some error occured while updating the data", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{roll}")
  public ResponseEntity<?> deleteStudent(@PathVariable("roll") int rollNo) {
    LOG.info("Delete Student API Hit");
    boolean result = studentService.deleteStudentData(rollNo);
    if (result) {
      return ResponseEntity.status(HttpStatus.OK).body("Done dana done done");
    } else {
      return new ResponseEntity<>(
          "Some error occured while deleting the data", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/retrieveAllUsingJPQL")
  public ResponseEntity<?> retrieveAllStudentsUsingJPQL() {
    LOG.info("retrieveAllStudentsUsingJPQL API Hit");
    List<Student> studentList = studentService.fetchAllStudentUsingJPQL();
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    return new ResponseEntity<>("Database is empty right now", HttpStatus.NO_CONTENT);
  }

  @GetMapping("/retrieveAllUsingFirstAndLastName")
  public ResponseEntity<?> retrieveAllStudentsUsingFirstAndLastName(
      @RequestParam("firstName") String fName, @RequestParam("lastName") String lName) {
    LOG.info("retrieveAllStudentsUsingFirstAndLastName API Hit");
    List<Student> studentList = studentService.fetchAllStudentUsingFirstAndLastName(fName, lName);
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    return new ResponseEntity<>(
        "No data corresponding to first and last name " + fName + ", " + lName,
        HttpStatus.NOT_FOUND);
  }

  @GetMapping("/retrieveAllUsingNative")
  public ResponseEntity<?> retrieveAllStudentsUsingNative() {
    LOG.info("retrieveAllStudentsUsingNative API Hit");
    List<Student> studentList = studentService.fetchAllStudentUsingNative();
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    return new ResponseEntity<>("Database is empty right now", HttpStatus.NO_CONTENT);
  }

  @PutMapping("/updateLastNameUsingFirstName")
  public ResponseEntity<?> updateAllStudentsLastNameUsingFirst(
      @RequestParam("firstName") String fName, @RequestParam("lastName") String lName) {
    LOG.info("updateAllStudentsLastNameUsingFirst API Hit");
    List<Student> studentList = studentService.updateStudentLastName(fName, lName);
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    return new ResponseEntity<>("No data corresponding to name " + fName, HttpStatus.NOT_FOUND);
  }

  @GetMapping("/retrieveAllStudentsByFirstName/{firstName}")
  public ResponseEntity<?> retrieveAllStudentsByFirstName(@PathVariable("firstName") String fName) {
    LOG.info("retrieveAllStudentsByFirstName API Hit");
    List<Student> studentList = studentService.findStudentsByFirstName(fName);
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    //    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Database is empty right now");
    // when we are using above statement then the message that we are sending to the output is not
    // displayed
    // neither the status the only thing we are able to see is {} so use below method always.
    return new ResponseEntity<>("No data corresponding to name " + fName, HttpStatus.NOT_FOUND);
  }

  @GetMapping("/retrieveAllStudentsByFirstAndLastName/{firstName}/{lastName}")
  public ResponseEntity<?> retrieveAllStudentsByFirstAndLastName(
      @PathVariable("firstName") String fName, @PathVariable("lastName") String lName) {
    LOG.info("retrieveAllStudentsByFirstAndLastName API Hit");
    List<Student> studentList = studentService.fetchAllStudentUsingFirstAndLastName(fName, lName);
    if (!studentList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
    return new ResponseEntity<>(
        "No data corresponding to first and last name " + fName + ", " + lName,
        HttpStatus.NOT_FOUND);
  }
}
