package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Student;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StudentService {
    List<Student> selectAll();
    Student selectByNumber(String slumber);
    Integer Login(String slumber, String pass, Integer identity,HttpServletRequest request);
}
