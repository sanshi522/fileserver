package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.bean.Teacher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TeacherService {
    List<Student> selectAll();
    Student selectByName(String name);
    Integer Login(String name, String pass,Integer identity, HttpServletRequest request);
    List<Teacher> findTeacherByClassId(Integer classId);
    List<Teacher> findAdmin();
}
