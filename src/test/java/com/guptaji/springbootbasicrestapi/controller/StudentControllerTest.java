package com.guptaji.springbootbasicrestapi.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guptaji.springbootbasicrestapi.entity.Student;
import com.guptaji.springbootbasicrestapi.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/*
   Below both the annotations '@AutoConfigureMockMvc' and '@ExtendWith(MockitoExtension.class)' are optional
   because @WebMvcTest already contains @AutoConfigureMockMvc. So if we want we can comment them as well.
*/

// @AutoConfigureMockMvc
// @ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private StudentServiceImpl studentService;

  @Autowired private ObjectMapper objectMapper;

  private Student student;
  private List<Student> studentList;

  @BeforeEach
  public void init() {
    student = new Student("Naruto", "Uzumaki", "Hokage", 7);
    studentList = new ArrayList<>();
    studentList.add(student);
  }

  @Test
  @DisplayName("Testing Insert Student API")
  void insertNewStudent() throws Exception {
    /*
       1-> Below if we will not import BDDMockito statically like this -
               import static org.mockito.BDDMockito.*;
           then we have to call BDDMockito.given().

       2-> Instead of ArgumentMatchers we can also write
               given(studentService.addNewStudentData(student)).willReturn(student);

       3-> below we will mock the StudentService
    */
    given(studentService.addNewStudentData(ArgumentMatchers.any())).willReturn(student);

    /*
       1-> Now we need to mock the API call using MockMvc

       2-> if we will not statically import MockMvcRequestBuilders like below
           import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
           then we have to use 'post() method' like this MockMvcRequestBuilders.post()
    */

    ResultActions response =
        mockMvc.perform(
            post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

    /*
       If we do not import 'MockMvcResultHandlers' statically like below -
           import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
       then we need to use 'MockMvcResultHandlers.print()'

       the same case with MockMvcResultMatchers.status()
           import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

       the same case with CoreMatchers.is()
           import static org.hamcrest.CoreMatchers.*;
    */
    response
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.firstName", is(student.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(student.getLastName())))
        .andExpect(jsonPath("$.className", is(student.getClassName())))
        .andExpect(jsonPath("$.rollNo").value(7));
  }

  @Test
  @DisplayName("Testing Fetch All Student API")
  void fetchAllStudents() throws Exception {
    given(studentService.getAllStudentData()).willReturn(studentList);

    ResultActions response = mockMvc.perform(get("/student"));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(studentList.size()))
        .andExpect(jsonPath("$[0].firstName", is(studentList.get(0).getFirstName())));
  }

  @Test
  @DisplayName("Testing Get Student API By Roll No")
  void getStudentByRollNo() throws Exception {
    given(studentService.getStudentByRoll(ArgumentMatchers.any(Integer.class))).willReturn(student);

    ResultActions response = mockMvc.perform(get("/student/{rollNo}", student.getRollNo()));

    response
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.firstName", is(student.getFirstName())))
        .andExpect(jsonPath("$.lastName").value(student.getLastName()));
  }

  @Test
  @DisplayName("Testing Get Student API By Roll No Using Query Variable")
  void getStudentByRollNoUsingQueryVariables() throws Exception {
    given(studentService.getStudentByRoll(ArgumentMatchers.any(Integer.class))).willReturn(student);

    ResultActions response =
        mockMvc.perform(
            get("/student/queryvariableUsage")
                .param("roll", Integer.toString(student.getRollNo())));

    response
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(jsonPath("$.firstName", is(student.getFirstName())))
        .andExpect(jsonPath("$.lastName").value(student.getLastName()));
  }

  @Test
  @DisplayName("Testing Update Student API")
  void updateStudent() throws Exception {
    given(studentService.updateStudentData(ArgumentMatchers.any(Student.class))).willReturn(true);

    ResultActions response =
        mockMvc.perform(
            put("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

    response
        .andDo(print())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is("Done dana done done")));
  }

  @Test
  @DisplayName("Testing Delete Student API By Roll No")
  void deleteStudent() throws Exception {

    // Here we will test the negative scenario
    given(studentService.deleteStudentData(ArgumentMatchers.any(Integer.class))).willReturn(false);

    ResultActions response = mockMvc.perform(delete("/student/{rollNo}", student.getRollNo()));

    response
        .andDo(print())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$").value("Some error occured while deleting the data"));
  }

  @Test
  void retrieveAllStudentsUsingJPQL() throws Exception {
    given(studentService.fetchAllStudentUsingJPQL()).willReturn(studentList);

    ResultActions response = mockMvc.perform(get("/student/retrieveAllUsingJPQL"));

    response
        .andDo(print())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.size()").value(1));
  }

  @Test
  void retrieveAllStudentsUsingFirstAndLastName() throws Exception {
    given(
            studentService.fetchAllStudentUsingFirstAndLastName(
                ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
        .willReturn(studentList);

    ResultActions response =
        mockMvc.perform(
            get("/student/retrieveAllUsingFirstAndLastName")
                .param("firstName", student.getFirstName())
                .param("lastName", student.getLastName()));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.size()").value(1));
  }

  @Test
  void retrieveAllStudentsUsingNative() throws Exception {
    given(studentService.fetchAllStudentUsingNative()).willReturn(studentList);

    ResultActions response = mockMvc.perform(get("/student/retrieveAllUsingNative"));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.size()", is(studentList.size())));
  }

  @Test
  void updateAllStudentsLastNameUsingFirst() throws Exception {
    String LAST_NAME = "Namikaze";

    // Here when we are trying to provide one value as argumentMatcher.any() and one String value
    // directly then it throws an error that either provide both values raw or both values as
    // argumentMatcher.
    Student updatedStudentData =
        new Student(student.getFirstName(), LAST_NAME, student.getClassName(), student.getRollNo());
    given(
            studentService.updateStudentLastName(
                ArgumentMatchers.any(String.class), ArgumentMatchers.eq(LAST_NAME)))
        .willReturn(List.of(updatedStudentData));

    ResultActions response =
        mockMvc.perform(
            put("/student/updateLastNameUsingFirstName")
                .param("firstName", student.getFirstName())
                .param("lastName", LAST_NAME));

    response
        .andDo(print())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$[0].lastName", is(updatedStudentData.getLastName())));
  }

  @Test
  void retrieveAllStudentsByFirstName() throws Exception {
    given(studentService.findStudentsByFirstName(ArgumentMatchers.any(String.class)))
        .willReturn(studentList);

    ResultActions response =
        mockMvc.perform(get("/student/retrieveAllStudentsByFirstName/{firstName}", "Naruto"));

    response
        .andDo(print())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$[0].rollNo").value(studentList.get(0).getRollNo()));
  }

  @Test
  void retrieveAllStudentsByFirstAndLastName() throws Exception {
    given(studentService.fetchAllStudentUsingFirstAndLastName("Naruto", "Uzumaki"))
        .willReturn(studentList);

    ResultActions response =
        mockMvc
            .perform(
                get(
                    "/student/retrieveAllStudentsByFirstAndLastName/{firstName}/{lastName}",
                    "Naruto",
                    "Uzumaki"))
            .andDo(print())
            .andExpect(jsonPath("$.size()", is(studentList.size())))
            .andExpect(jsonPath("$[0].className").value(studentList.get(0).getClassName()));
  }
}
