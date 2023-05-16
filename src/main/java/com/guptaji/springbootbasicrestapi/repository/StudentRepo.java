package com.guptaji.springbootbasicrestapi.repository;

import com.guptaji.springbootbasicrestapi.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
   Here @Repository annotation is not required because we are using JpaRepository and JpaRepository's implementation
   class 'SimpleJpaRepository' (check with ctrl+alt+B) already provides @Repository annotation so providing here is optional.
   and we can directly Autowired it wherever we want.
*/
@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {}
