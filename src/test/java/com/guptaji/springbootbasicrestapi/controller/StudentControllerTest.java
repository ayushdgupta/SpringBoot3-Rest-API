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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

  @BeforeEach
  public void init() {
    student = new Student("Naruto", "Uzumaki", "Hokage", 7);
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
        .andExpect(jsonPath("$.firstName", is(student.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(student.getLastName())))
        .andExpect(jsonPath("$.className", is(student.getClassName())));
  }

  @Test
  @Disabled
  void fetchAllStudents() {}

  @Test
  @Disabled
  void getStudentByRollNo() {}

  @Test
  @Disabled
  void getStudentByRollNoUsingQueryVariables() {}

  @Test
  @Disabled
  void updateStudent() {}

  @Test
  @Disabled
  void deleteStudent() {}
}
