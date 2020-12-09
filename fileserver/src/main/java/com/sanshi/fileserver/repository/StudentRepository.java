package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAll();
    List<Student> findByStuNumber(String stuNumber);
    void deleteById(Integer stuId);
}
