package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.vo.PageGet;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> selectAll();
    Student findOneById(Integer id);
    Student selectByNumber(String slumber);
    Integer Login(String slumber, String pass, Integer identity,HttpServletRequest request);
    List<Student> findAllByStuGroup(Integer stuGroup);
    Map findAllByClassId(PageGet pageGet);
    Student save(Student student);
    Integer deleteById(Integer val);
    int  saveStudents(List<Student> studentList);
}
