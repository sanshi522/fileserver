package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    List<Teacher> findByTeaName(String teaName);
    void deleteByTeaId(Integer teaId);
}
