package com.guptaji.springbootbasicrestapi.repository;

import com.guptaji.springbootbasicrestapi.entity.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
   Here @Repository annotation is not required because we are using JpaRepository and JpaRepository's implementation
   class 'SimpleJpaRepository' (check with ctrl+alt+B) already provides @Repository annotation so providing here is optional.
   and we can directly Autowired it wherever we want.
*/
@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

  // A JPQL Query to fetch all Students from DB
  @Query("select student from Student student")
  public List<Student> getAllStudentsUsingJPQL();

  // A JPQL Query to fetch the student on behalf of first and last name
  // here we use @Param annotation to bind function parameters to query one
  // in below JPQL Query when we pass column name like they created in DB e.g.
  // "select st from Student st where st.first_name = :fName and st.last_name = :lName"
  // then we get parse error, so we need to provide column name be like present in our entity.
  @Query("select st from Student st where st.firstName = :fName and st.lastName = :lName")
  public List<Student> getAllStudentsUsingFirstAndLastName(
      @Param("fName") String firstName, @Param("lName") String lastName);

  // A JPQL Query to Update the student data
  // If we are writing custom JPQL Queries for Any manipulation of data like update, delete then we
  // need
  // to use two annotations @Transactional, @Modifying, it'll allow spring to do updates.
  // Here we add 'clearAutomatically = true' in @Modifying because it's JUnit were failing before
  // adding it.
  // due to some spring's internal issue.
  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("update Student st set st.lastName = :lName where st.firstName = :fName")
  public void updateLastNameUsingFirstByJPQL(
      @Param("fName") String firstName, @Param("lName") String lastName);

  // Native Queries : When we define query as native, it means it is specific to a particular
  // database.
  // i.e. if we are using postgre db then in the query we are allowed to use Postgre DB specific
  // things

  // A Native query to fetch all students from DB
  @Query(value = "select * from Student", nativeQuery = true)
  public List<Student> getAllStudentsUsingNative();

  // Custom Finder Methods / Derived Query Methods

  public List<Student> findByFirstName(String fName);

  public List<Student> findByFirstNameOrLastName(String fName, String lName);
}
